package pl.ipebk.solver

/**
 * Solver class. Tells if set of cards is 'Set' in the meaning of set game
 */
internal class SetMatcher(private val set : Set) {
    private val numberOfFeatures: Int

    init {
        val listOfFeatureNumbers = set.cards.map { it!!.features.size }
        if(listOfFeatureNumbers.distinct().size != 1)
            throw IllegalArgumentException("number of features in cards is not equal")

        numberOfFeatures = listOfFeatureNumbers.first()
    }

    /**
     * if set of cards is 'Set' in the meaning of set game.
     * Set of cards is 'Set' if each of its card's features is either different or the same
     * in all the cards
     */
    fun isSet() : Boolean {
        set.cards.first()!!.features.forEach { feature ->
            val featureValues = set.cards.map {
                it!!.features.find { it.featureId == feature.featureId }!!.value
            }
            if(!FeatureMatcher(featureValues).isMatching()) return false
        }
        return true
    }
}