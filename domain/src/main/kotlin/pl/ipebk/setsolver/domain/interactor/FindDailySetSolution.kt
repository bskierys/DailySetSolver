package pl.ipebk.setsolver.domain.interactor

import io.reactivex.Single
import pl.ipebk.setsolver.domain.DailySetEngine
import pl.ipebk.setsolver.domain.DailySetRemote
import pl.ipebk.setsolver.domain.SetSolution

/**
 * Use case used for retrieving and solving daily set puzzle
 */
class FindDailySetSolution(private val setEngine: DailySetEngine,
                           private val remote: DailySetRemote) :
  SingleUseCase<SetSolution, Void?>(){

  override fun buildUseCaseObservable(params: Void?): Single<SetSolution> {
    return remote.getPuzzleCards()
      .flatMap { setEngine.getSolution(it) }
  }
}