package pl.ipebk.setsolver.domain

import java.util.*

data class SetSolution(val puzzleDate: Date, val sets: List<SetCardThreePack>)

data class SetCardThreePack(val card1: SetCard, val card2: SetCard, val card3: SetCard)

data class SetPuzzle(val puzzleDate: Date, val cards: List<SetCard>)

data class SetCard(val shading: Shading, val symbol: Symbol,
                   val color: Color, val count: Count)

enum class Symbol { SQUIGGLE, DIAMOND, OVAL }
enum class Shading { SOLID, STRIPED, OUTLINED }
enum class Color { RED, PURPLE, GREEN }
enum class Count { ONE, TWO, THREE }