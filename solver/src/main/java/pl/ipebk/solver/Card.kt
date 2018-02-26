package pl.ipebk.solver

/**
 * Creates card with given feature variants. Feature ids are incrementing and starts with 0
 * If you like to add custom features. Please use default [Card] constructor.
 *
 * @param featureVariants list of variants for default features
 */
class Card(vararg featureVariants: Int) {
    val features = ArrayList<Feature>()
    init {
        var featureId = 0
        featureVariants.forEach {
            features.add(Feature(featureId, it))
            featureId ++
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Card

        val common = features.intersect(this.features)
        return common.size == this.features.size
    }

    override fun hashCode(): Int {
        return features.hashCode()
    }
}