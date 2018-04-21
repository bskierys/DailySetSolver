package pl.ipebk.setsolver.remote.mapper

import org.junit.Assert
import org.junit.Test

class CardNumberParserTest {
  private val parser = CardNumberParser()

  @Test
  fun `should find names when only numbers present`() {
    val names = arrayListOf(
      "/images/setcards/small/12.gif",
      "/images/setcards/small/20.gif",
      "/images/setcards/small/81.gif")

    val expected = listOf(12, 20, 81)

    Assert.assertArrayEquals(expected.toIntArray(), parser.parseCardNumbers(names).toIntArray())
  }

  @Test
  fun `should find names when one digit numbers present`() {
    val names = arrayListOf(
      "/images/setcards/small/02.gif",
      "/images/setcards/small/20.gif")

    val expected = listOf(2, 20)

    Assert.assertArrayEquals(expected.toIntArray(), parser.parseCardNumbers(names).toIntArray())
  }

  @Test
  fun `should find names when noise is present`() {
    val names = arrayListOf(
      "fancy_instagram_image.png",
      "/images/setcards/small/02.gif",
      "/images/setcards/small/20.gif",
      "another_image.png")

    val expected = listOf(2, 20)

    Assert.assertArrayEquals(expected.toIntArray(), parser.parseCardNumbers(names).toIntArray())
  }
}