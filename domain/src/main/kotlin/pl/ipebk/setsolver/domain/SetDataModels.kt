package pl.ipebk.setsolver.domain

data class SetSolution(val sets: List<SetCardThreePack>)

data class SetCardThreePack(val card1: SetCard, val card2: SetCard, val card3: SetCard)

data class SetCard(val shading: Shading, val symbol: Symbol,
                   val color: Color, val count: Count)

enum class Symbol { SQUIGGLE, DIAMOND, OVAL }
enum class Shading { SOLID, STRIPED, OUTLINED }
enum class Color { RED, PURPLE, GREEN }
enum class Count { ONE, TWO, THREE }