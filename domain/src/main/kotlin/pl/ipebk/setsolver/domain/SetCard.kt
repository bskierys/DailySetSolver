package pl.ipebk.setsolver.domain

data class SetCard(val shading: Shading, val symbol: Symbol,
                   val color: Color, val count: Count)

enum class Symbol { SQUIGGLE, DIAMOND, OVAL }
enum class Shading { SOLID, STRIPED, OUTLINED }
enum class Color { RED, PURPLE, GREEN }
enum class Count { ONE, TWO, THREE }