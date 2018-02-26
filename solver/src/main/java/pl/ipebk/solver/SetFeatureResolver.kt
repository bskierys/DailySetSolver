package pl.ipebk.solver

/**
 * TODO: Generic description. Replace with real one.
 */
internal class SetFeatureResolver(private val set: Set, private val featureId: Int) {
    init {
        val numberOfCardsWithFeature = set.cards.filter {
            it!!.features.filter { it.featureId == featureId }.isNotEmpty()
        }.size

        if(numberOfCardsWithFeature != set.cards.size)
            throw IllegalArgumentException("not all of cards has given feature")
    }

    /**
     * todo: javadoc
     */
    fun isMatching() : Boolean {
        return isFeatureDifferent(set, featureId) || isFeatureSame(set, featureId)
    }

    private fun isFeatureDifferent(set: Set, featureId: Int) : Boolean {
        val values = set.cards.map {
            it!!.features.find { it.featureId == featureId }
        } as ArrayList

        values.allCombinations(2).forEach {
            if(it[0] == it[1]) return false
        }

        return true
    }

    private fun isFeatureSame(set: Set, featureId: Int) : Boolean {
        val values = set.cards.map {
            it!!.features.find { it.featureId == featureId }
        } as ArrayList

        values.allCombinations(2).forEach {
            if(it[0] != it[1]) return false
        }

        return true
    }
}