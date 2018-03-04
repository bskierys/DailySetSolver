package pl.ipebk.setsolver.ui.solver

import android.net.NetworkInfo
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import io.github.plastix.rxschedulerrule.RxSchedulerRule
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import pl.ipebk.setsolver.data.network.NetworkInteractor
import pl.ipebk.setsolver.domain.SetSolution
import pl.ipebk.setsolver.domain.interactor.FindDailySetSolution

class SolverViewModelTest {
  @get:Rule
  @Suppress("unused")
  val schedulerRule = RxSchedulerRule()

  @Mock
  private lateinit var findSolution: FindDailySetSolution

  @Mock
  private lateinit var networkInteractor: NetworkInteractor

  private lateinit var viewModel: SolverViewModel

  private lateinit var testDailyPuzzleSubscriber: DisposableSingleObserver<SetSolution>

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)

    viewModel = TestSolverViewModel(networkInteractor, findSolution)
    viewModel.bind()
  }

  @Test
  fun shouldShowSolution_whenNoError() {
    Mockito.`when`(networkInteractor.hasNetworkConnectionCompletable())
      .thenReturn(Completable.complete())

    val stubSolutionStream = PublishSubject.create<SetSolution>()
    testDailyPuzzleSubscriber = SubjectDisposableSingleObserver(stubSolutionStream)

    viewModel.fetchAndSolveDaily()
    viewModel.loadingStateStream.test().assertValue(true)
    stubSolutionStream.test().assertNoErrors()
  }

  @Test
  fun shouldShowError_whenNoInternet() {
    val error = NetworkInteractor.NetworkUnavailableException()
    Mockito.`when`(networkInteractor.hasNetworkConnectionCompletable())
      .thenReturn(
        Completable.error(error)
      )

    val stubSolutionStream = PublishSubject.create<SetSolution>()
    testDailyPuzzleSubscriber = SubjectDisposableSingleObserver(stubSolutionStream)

    val connectivity = Connectivity.Builder().state(NetworkInfo.State.DISCONNECTED).build()
    val stubNetworkObserver = Observable.just(connectivity)

    Mockito.`when`(networkInteractor.observeConnection()).thenReturn(stubNetworkObserver)

    val errorObserver = viewModel.networkErrorStream.test().awaitCount(1)
    val loadingObserver = viewModel.loadingStateStream.test()
    viewModel.fetchAndSolveDaily()

    errorObserver.assertValueCount(1).assertValue(error)
    loadingObserver.assertValues(true, true, false)
  }

  @Test
  fun shouldShowSolutionWhenInternetBackOn() {
    val error = NetworkInteractor.NetworkUnavailableException()
    Mockito.`when`(networkInteractor.hasNetworkConnectionCompletable())
      .thenReturn(
        Completable.error(error)
      )

    val stubSolutionStream = PublishSubject.create<SetSolution>()
    testDailyPuzzleSubscriber = SubjectDisposableSingleObserver(stubSolutionStream)

    val connectivity = Connectivity.Builder().state(NetworkInfo.State.CONNECTED).build()
    val stubNetworkObserver = Observable.just(connectivity)

    Mockito.`when`(networkInteractor.observeConnection()).thenReturn(stubNetworkObserver)

    viewModel.fetchAndSolveDaily()
    viewModel.loadingStateStream.test().assertValue(true)
  }

  class SubjectDisposableSingleObserver<T>(private val subject: PublishSubject<T>)
    : DisposableSingleObserver<T>() {
    override fun onSuccess(t: T) {
      subject.onNext(t)
      subject.onComplete()
    }

    override fun onError(e: Throwable) {
      subject.onError(e)
    }
  }

  inner class TestSolverViewModel(networkInteractor: NetworkInteractor,
                                  findDailySetSolution: FindDailySetSolution)
    : SolverViewModel(networkInteractor, findDailySetSolution) {
    override fun getDailyPuzzleSubscriber(): DisposableSingleObserver<SetSolution> {
      return testDailyPuzzleSubscriber
    }
  }
}