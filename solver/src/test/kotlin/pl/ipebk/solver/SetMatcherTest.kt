package pl.ipebk.solver

import org.junit.Assert
import org.junit.Test

class SetMatcherTest {
    @Test
    fun `should throw exception when number of features not equal`() {
        try {
            val set = Set(listOf(
                    Card(2, 2, 1),
                    Card(1, 2, 2, 2),
                    Card(0, 0, 0, 1)
            ))
            SetMatcher(set)
            Assert.fail("should throw exception")
        } catch (ex: IllegalArgumentException) {
            // exception expected
        }
    }

    @Test
    fun `should detect set with 4 features`() {
        val set = Set(listOf(
                Card(2, 1, 1, 0),
                Card(1, 2, 2, 2),
                Card(0, 0, 0, 1)
        ))
        Assert.assertTrue(SetMatcher(set).isSet())
    }

    @Test
    fun `should detect NOT set with 4 features`() {
        val set = Set(listOf(
                Card(2, 2, 1, 0),
                Card(1, 2, 2, 2),
                Card(0, 0, 0, 1)
        ))
        Assert.assertFalse(SetMatcher(set).isSet())
    }

    @Test
    fun `should detect set with 2 features`() {
        val set = Set(listOf(
                Card(2, 1),
                Card(1, 2)
        ))
        Assert.assertTrue(SetMatcher(set).isSet())
    }

    @Test
    fun `should detect NOT set with 2 features`() {
        val set = Set(listOf(
                Card(2, 1),
                Card(2, 2)
        ))
        Assert.assertTrue(SetMatcher(set).isSet())
    }
}