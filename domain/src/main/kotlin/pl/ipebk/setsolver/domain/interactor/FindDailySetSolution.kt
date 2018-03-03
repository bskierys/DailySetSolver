package pl.ipebk.setsolver.domain.interactor

import io.reactivex.Single
import pl.ipebk.setsolver.domain.DailySetEngine
import pl.ipebk.setsolver.domain.DailySetRemote
import pl.ipebk.setsolver.domain.SetSolution
import pl.ipebk.setsolver.domain.executor.PostExecutionThread
import pl.ipebk.setsolver.domain.executor.ThreadExecutor
import javax.inject.Inject

/**
 * Use case used for retrieving and solving daily set puzzle
 */
class FindDailySetSolution @Inject constructor(private val setEngine: DailySetEngine,
                           private val remote: DailySetRemote,
                           threadExecutor: ThreadExecutor,
                           postExecutionThread: PostExecutionThread) :
  SingleUseCase<SetSolution, Void?>(threadExecutor, postExecutionThread){

  override fun buildUseCaseObservable(params: Void?): Single<SetSolution> {
    return remote.getPuzzleCards()
      .flatMap { setEngine.getSolution(it) }
  }
}