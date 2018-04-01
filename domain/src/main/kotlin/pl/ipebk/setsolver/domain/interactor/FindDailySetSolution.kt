package pl.ipebk.setsolver.domain.interactor

import io.reactivex.Scheduler
import io.reactivex.Single
import pl.ipebk.setsolver.domain.DailySetEngine
import pl.ipebk.setsolver.domain.DailySetRemote
import pl.ipebk.setsolver.domain.SetSolution

/**
 * Use case used for retrieving and solving daily set puzzle
 */
// open to be able to mock with mockito
open class FindDailySetSolution(private val setEngine: DailySetEngine,
                                private val remote: DailySetRemote,
                                backgroundScheduler: Scheduler,
                                foregroundScheduler: Scheduler) :
  SingleUseCase<SetSolution, Void?>(backgroundScheduler, foregroundScheduler) {

  override fun buildUseCaseObservable(params: Void?): Single<SetSolution> {
    return remote.getPuzzleCards()
      .flatMap { setEngine.getSolution(it) }
  }
}