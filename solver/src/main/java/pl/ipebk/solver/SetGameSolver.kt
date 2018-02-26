package pl.ipebk.solver

/**
 * Generic game solver. It solves 'Set Game' for given number of features and their variants.
 * In classic set game we have 4 features, each with 3 variants
 *
 * @param numberOfFeatures number of features to solve game with. In classic game: 4
 * @param numberOfFeatureVariants number of variants for each feature. In classic game: 3
 */
class SetGameSolver(private val numberOfFeatures: Int, private val numberOfFeatureVariants: Int) {
    // each set will contain same number of cards as number of feature variants
    // this is implication of the fact that every card in set has different feature variant
    private val numbersOfCardsInSet = numberOfFeatureVariants

    /**
     * Finds solution for given list of cards. Throws exceptions if given cards does not match
     * [numberOfFeatures] or [numberOfFeatureVariants]
     *
     * @return solution for game as list of [Set] object
     */
    fun findSolution(cards: List<Card>): List<Set> {
        validate(cards)

        return ArrayList(cards)
                .allCombinations(numbersOfCardsInSet)
                .map { Set(it) }
                .filter { SetMatcher(it).isSet() }
    }

    private fun validate(cards: List<Card>) {
        if (cards.isEmpty())
            throw IllegalArgumentException("empty cards list")

        if (cards.size < numberOfFeatureVariants)
            throw IllegalArgumentException("fewer cards than feature variations")

        cards.forEach {
            if(it.features.size != numberOfFeatures)
                throw IllegalArgumentException("one of cards has not valid amount of features")

            it.features.forEach {
                if (it.value < 0 || it.value >= numberOfFeatureVariants)
                    throw IllegalArgumentException("one of card's features out of range")
            }
        }

        cards.groupingBy { it }.eachCount().forEach {
            if (it.value > 1) throw IllegalArgumentException("two identical cards detected")
        }
    }
}