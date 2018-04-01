package pl.ipebk.solver

/**
 * Representation of Set in SetGame
 */
data class Set internal constructor(private val numberOfCards: Int) {
  var cards = Array<Card?>(numberOfCards, { null })
    private set

  constructor(cards: List<Card>) : this(cards.size) {
    this.cards = cards.toTypedArray()
  }
}