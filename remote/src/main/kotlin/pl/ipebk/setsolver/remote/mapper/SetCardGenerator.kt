package pl.ipebk.setsolver.remote.mapper

import pl.ipebk.setsolver.domain.*

/**
 * Generates set cards in same way as they are ordered on server. Implements [Iterator] for
 * simpler usage
 */
internal class SetCardGenerator : Iterator<SetCard> {
  private companion object {
    const val START_VALUE = 0
    const val MAX_VALUE = 2
  }

  private var currentShadingOrdinal = START_VALUE
  private var currentSymbolOrdinal = START_VALUE
  private var currentColorOrdinal = START_VALUE
  private var currentCountOrdinal = START_VALUE

  override fun hasNext(): Boolean {
    return currentShadingOrdinal < MAX_VALUE
      || currentSymbolOrdinal < MAX_VALUE
      || currentColorOrdinal < MAX_VALUE
      || currentCountOrdinal < MAX_VALUE
  }

  override fun next(): SetCard {
    val card = SetCard(
      Shading.values()[currentShadingOrdinal],
      Symbol.values()[currentSymbolOrdinal],
      Color.values()[currentColorOrdinal],
      Count.values()[currentCountOrdinal]
    )

    increment()
    return card
  }

  private fun increment() {
    incrementCount()
  }

  private fun incrementCount() {
    if (currentCountOrdinal < MAX_VALUE) {
      currentCountOrdinal ++
    } else if (currentCountOrdinal == MAX_VALUE) {
      currentCountOrdinal = START_VALUE
      incrementColor()
    }
  }

  private fun incrementColor() {
    if (currentColorOrdinal < MAX_VALUE) {
      currentColorOrdinal ++
    } else if (currentColorOrdinal == MAX_VALUE) {
      currentColorOrdinal = START_VALUE
      incrementSymbol()
    }
  }

  private fun incrementSymbol() {
    if (currentSymbolOrdinal < MAX_VALUE) {
      currentSymbolOrdinal ++
    } else if (currentSymbolOrdinal == MAX_VALUE) {
      currentSymbolOrdinal = START_VALUE
      incrementShading()
    }
  }

  private fun incrementShading() {
    if (currentShadingOrdinal < MAX_VALUE) {
      currentShadingOrdinal ++
    } else if (currentShadingOrdinal == MAX_VALUE) {
      throw IndexOutOfBoundsException("has no next element")
    }
  }
}