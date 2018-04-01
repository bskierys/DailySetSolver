package pl.ipebk.solver

import org.junit.Assert
import org.junit.Test

class CombinationsTest {
  @Test
  fun `should throw exception when k greater than n`() {
    try {
      Combinations(4, 3)
      Assert.fail("expected exception")
    } catch (ex: IllegalArgumentException) {
      // expected exception
    }
  }

  @Test
  fun `should compute variations of 3 3`() {
    val combinations = Combinations(3, 3)
    Assert.assertEquals(1, combinations.computed.size)
    Assert.assertArrayEquals(arrayOf(0, 1, 2), combinations.computed[0].toArray())
  }

  @Test
  fun `should compute variations of 3 4`() {
    val combinations = Combinations(3, 4)
    Assert.assertEquals(4, combinations.computed.size)
  }

  @Test
  fun `should compute variations of 2 4`() {
    val combinations = Combinations(2, 4)
    Assert.assertEquals(6, combinations.computed.size)
  }

  @Test
  fun `should compute variations of 3 12`() {
    val combinations = Combinations(3, 12)
    Assert.assertEquals(220, combinations.computed.size)
  }
}