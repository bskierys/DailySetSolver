package pl.ipebk.setsolver.remote

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.Assert
import org.junit.Test
import java.io.File

class SetResponseParserTest {
  @Test
  fun `should get list of card numbers from valid file 1`() {
    val dailySetDocument = loadDocumentForFile("/daily_set_43_75.htm")

    val expected = intArrayOf(43, 33, 9, 67, 77, 61, 23, 10, 5, 37, 60, 75)
    val actual = SetResponseParser(dailySetDocument).parse().toIntArray()

    Assert.assertArrayEquals(expected, actual)
  }

  @Test
  fun `should get list of card numbers from valid file 2`() {
    val dailySetDocument = loadDocumentForFile("/daily_set_16_70.htm")

    val expected = intArrayOf(16, 5, 10, 9, 57, 79, 7, 77, 37, 39, 43, 70)
    val actual = SetResponseParser(dailySetDocument).parse().toIntArray()

    Assert.assertArrayEquals(expected, actual)
  }

  private fun loadDocumentForFile(fileName: String): Document {
    val url = this.javaClass.getResource(fileName)
    val loadedFile = File(url.file)

    return Jsoup.parse(
      loadedFile,
      "UTF-8")
  }
}