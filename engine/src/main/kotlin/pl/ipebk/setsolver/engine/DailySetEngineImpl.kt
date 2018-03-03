package pl.ipebk.setsolver.engine

import io.reactivex.Single
import pl.ipebk.setsolver.domain.DailySetEngine
import pl.ipebk.setsolver.domain.SetCard
import pl.ipebk.setsolver.domain.SetCardThreePack
import pl.ipebk.setsolver.domain.SetSolution
import pl.ipebk.solver.SetGameSolver
import javax.inject.Inject

/**
 * Engine implementation to solve puzzles. This class implements the [DailySetEngine] from the
 * Domain layer
 */
class DailySetEngineImpl @Inject constructor(private val solver: SetGameSolver,
                         private val mapper: CardMapper) : DailySetEngine {

  override fun getSolution(cards: List<SetCard>): Single<SetSolution> {
    return Single.create {
      try {
        val solution = findSolutionAndMapIt(cards)
        if(!it.isDisposed)
          it.onSuccess(solution)
      } catch (ex: Throwable) {
        if(!it.isDisposed)
          it.onError(ex)
      }
    }
  }

  private fun findSolutionAndMapIt(cards: List<SetCard>) : SetSolution {
    val solverCards = cards.map { mapper.mapToSolverModel(it) }

    val solutionCards = solver.findSolution(solverCards).map {
      val card1 = mapper.mapFromSolverModel(it.cards[0]!!)
      val card2 = mapper.mapFromSolverModel(it.cards[1]!!)
      val card3 = mapper.mapFromSolverModel(it.cards[2]!!)
      SetCardThreePack(card1, card2, card3)
    }
    return SetSolution(solutionCards)
  }
}