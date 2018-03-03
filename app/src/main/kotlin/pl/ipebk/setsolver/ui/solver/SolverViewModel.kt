package pl.ipebk.setsolver.ui.solver

import io.github.plastix.rxdelay.RxDelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import pl.ipebk.setsolver.data.network.NetworkInteractor
import pl.ipebk.setsolver.data.remote.model.Repo
import pl.ipebk.setsolver.domain.SetSolution
import pl.ipebk.setsolver.domain.interactor.FindDailySetSolution
import pl.ipebk.setsolver.ui.base.RxViewModel
import javax.inject.Inject

class SolverViewModel @Inject constructor(
  private val networkInteractor: NetworkInteractor,
  private val findSolutionUseCase: FindDailySetSolution) : RxViewModel() {

  private var networkRequest: Disposable = Disposables.disposed()

  private var solution: BehaviorSubject<SetSolution> = BehaviorSubject.create()
  private var loadingState: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(true)
  private val fetchErrors: PublishSubject<Throwable> = PublishSubject.create()
  private val networkErrors: PublishSubject<Throwable> = PublishSubject.create()

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
        }
      })
  }

  inner class DailyPuzzleSubscriber : DisposableSingleObserver<SetSolution>() {
    override fun onSuccess(sets: SetSolution) {
      loadingState.onNext(false)
      solution.onNext(sets)
    }

    override fun onError(e: Throwable) {
      loadingState.onNext(false)
      fetchErrors.onNext(e)
    }
  }

  //todo maybe delegated properties?
  fun getSolution(): Observable<SetSolution> = solution.hide()

  fun fetchErrors(): Observable<Throwable> = fetchErrors.hide()

  fun networkErrors(): Observable<Throwable> = networkErrors.hide()

  fun loadingState(): Observable<Boolean> = loadingState.hide()
}