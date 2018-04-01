package pl.ipebk.setsolver.engine

import pl.ipebk.setsolver.domain.*
import pl.ipebk.solver.Card
import javax.inject.Inject

/**
 * Map a [SetCard] to and from a [Card] instance when data is moving between this later and the
 * Domain layer
 */
class CardMapper @Inject constructor() {
  fun mapFromSolverModel(card: Card): SetCard {
    if (card.features.size != 4) {
      throw IllegalArgumentException("card should have 4 features")
    }

    card.features.forEach {
      if (it.value > 2) {
        throw IllegalArgumentException("one of cards has invalid feature")
      }
    }

    return SetCard(
      Shading.values()[card.features[0].value],
      Symbol.values()[card.features[1].value],
      Color.values()[card.features[2].value],
      Count.values()[card.features[3].value]
    )
  }

  fun mapToSolverModel(setCard: SetCard): Card {
    return Card(
      setCard.shading.ordinal,
      setCard.symbol.ordinal,
      setCard.color.ordinal,
      setCard.count.ordinal
    )
  }
}