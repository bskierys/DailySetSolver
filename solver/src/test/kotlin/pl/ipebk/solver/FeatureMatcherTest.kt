package pl.ipebk.solver

import org.junit.Assert.assertTrue
import org.junit.Test

class FeatureMatcherTest {
    @Test
    fun `should be matching when all features are different 1`() {
        val featureValues = listOf(2, 1, 0)
        assertTrue(FeatureMatcher(featureValues).isMatching())
    }

    @Test
    fun `should be matching when all features are different 2`() {
        val featureValues = listOf(0, 2, 3, 4)
        assertTrue(FeatureMatcher(featureValues).isMatching())
    }

    @Test
    fun `should be matching when all features are same`() {
        val featureValues = listOf(2, 2, 2)
        assertTrue(FeatureMatcher(featureValues).isMatching())
    }
}