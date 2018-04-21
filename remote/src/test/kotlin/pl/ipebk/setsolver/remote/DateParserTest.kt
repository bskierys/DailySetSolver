package pl.ipebk.setsolver.remote

import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.util.*

class DateParserTest {
  @get:Rule
  val exception: ExpectedException = ExpectedException.none()

  private val parser = DateParser()

  @Test
  fun `should find date from april and one-digit day`() {
    val date = "April 1, 2018"
    val fullDate = "Saturday, $date"

    val expected = Date(118,3,1)
    Assert.assertEquals(expected, parser.parseDate(fullDate))
  }

  @Test
  fun `should find date from september and two-digit day`() {
    val date = "September 28, 2018"
    val fullDate = "Monday, $date"

    val expected = Date(118,8,28)
    Assert.assertEquals(expected, parser.parseDate(fullDate))
  }

  @Test
  fun `should find date among noise`() {
    val date = "April 1, 2018"
    val fullDate = "Saturday, $date"
    val test = "Lorem ipsum dolor :$fullDate<> as DateGif"

    val expected = Date(118,3,1)
    Assert.assertEquals(expected, parser.parseDate(test))
  }

  @Test
  fun `should not find date when invalid day of week`() {
    val date = "April 1, 2018"
    val fullDate = "Fredas, $date"

    exception.expect(IllegalArgumentException::class.java)
    parser.parseDate(fullDate)
  }

  @Test
  fun `should not find date when invalid day of month`() {
    val date = "April 82, 2018"
    val fullDate = "Monday, $date"

    exception.expect(IllegalArgumentException::class.java)
    parser.parseDate(fullDate)
  }

  @Test
  fun `should not find date when invalid month`() {
    val date = "Kwiecien 12, 2018"
    val fullDate = "Monday, $date"

    exception.expect(IllegalArgumentException::class.java)
    parser.parseDate(fullDate)
  }

  @Test
  fun `should not find date when invalid year`() {
    val date = "April 12, 201"
    val fullDate = "Monday, $date"

    exception.expect(IllegalArgumentException::class.java)
    parser.parseDate(fullDate)
  }
}