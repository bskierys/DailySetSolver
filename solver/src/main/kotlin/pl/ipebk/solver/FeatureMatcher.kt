package pl.ipebk.solver

internal class FeatureMatcher(private val featureValues: List<Int>) {
    /**
     * list of features is matching if they are either all the same or all different
     */
    fun isMatching() : Boolean {
        return isFeatureDifferent() || isFeatureSame()
    }

    private fun isFeatureDifferent() : Boolean {
        return featureValues.distinct().size == featureValues.size
    }

    private fun isFeatureSame() : Boolean {
        return featureValues.distinct().size == 1
    }
}