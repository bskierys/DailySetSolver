package pl.ipebk.setsolver.ui.solver

import android.net.NetworkInfo
import com.github.pwittchen.reactivenetwork.library.rx2.ConnectivityPredicate
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.github.plastix.rxdelay.RxDelay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import pl.ipebk.setsolver.data.network.NetworkInteractor
import pl.ipebk.setsolver.domain.SetSolution
import pl.ipebk.setsolver.domain.interactor.FindDailySetSolution
import pl.ipebk.setsolver.ui.base.RxViewModel
import pl.ipebk.setsolver.ui.misc.SubjectDelegate
import timber.log.Timber
import javax.inject.Inject

class SolverViewModel @Inject constructor(
  private val networkInteractor: NetworkInteractor,
  private val findSolutionUseCase: FindDailySetSolution) : RxViewModel() {

  private var networkRequest: Disposable = Disposables.disposed()

  private var solution: BehaviorSubject<SetSolution> = BehaviorSubject.create()
  private var loadingState: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(true)
  private val genericErrors: PublishSubject<Throwable> = PublishSubject.create()
  private val networkErrors: PublishSubject<Throwable> = PublishSubject.create()

  internal val solutionStream by SubjectDelegate(solution)
  internal val loadingStateStream by SubjectDelegate(loadingState)
  internal val networkErrorStream by SubjectDelegate(networkErrors)
  internal val genericErrorStream by SubjectDelegate(genericErrors)

  fun fetchAndSolveDaily() {
    networkRequest = networkInteractor.hasNetworkConnectionCompletable()
      .subscribeOn(Schedulers.io())
      .doOnSubscribe { loadingState.onNext(true) }
      .compose(RxDelay.delayCompletable(getViewState()))
      .observeOn(AndroidSchedulers.mainThread())
      .doOnSubscribe {
        networkRequest.dispose() // Cancel any current running request
      }.subscribeWith(object : DisposableCompletableObserver() {
        override fun onComplete() {
          findSolutionUseCase.execute(DailyPuzzleSubscriber())
        }

        override fun onError(e: Throwable) {
          loadingState.onNext(false)
          networkErrors.onNext(e)
          loadWhenConnectionChanges()
        }
      })

    addDisposable(networkRequest)
  }

  private fun loadWhenConnectionChanges() {
    val connectionBackOn = networkInteractor.observeConnection()
      .subscribeOn(Schedulers.io())
      .filter { ConnectivityPredicate.hasState(NetworkInfo.State.CONNECTED).test(it) }
      .observeOn(AndroidSchedulers.mainThread())
      .doOnError { Timber.e(it)}
      .subscribe {
        loadingState.onNext(true)
        findSolutionUseCase.execute(DailyPuzzleSubscriber())
      }

    addDisposable(connectionBackOn)
  }

  inner class DailyPuzzleSubscriber : DisposableSingleObserver<SetSolution>() {
    override fun onSuccess(sets: SetSolution) {
      loadingState.onNext(false)
      solution.onNext(sets)
    }

    override fun onError(e: Throwable) {
      loadingState.onNext(false)
      genericErrors.onNext(e)
    }
  }
}