package pl.ipebk.setsolver.engine

import pl.ipebk.setsolver.domain.SetCard
import pl.ipebk.solver.Card

/**
 * Map a [SetCard] to and from a [Card] instance when data is moving between this later and the
 * Domain layer
 */
interface CardMapper {
  fun mapFromSolverModel(card: Card) : SetCard
  fun mapToSolverModel(setCard: SetCard) : Card
}