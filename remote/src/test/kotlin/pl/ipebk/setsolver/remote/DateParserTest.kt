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

    Assert.assertEquals(date, parser.findDate(fullDate))
  }

  @Test
  fun `should find date from september and two-digit day`() {
    val date = "September 28, 2018"
    val fullDate = "Monday, $date"

    Assert.assertEquals(date, parser.findDate(fullDate))
  }

  @Test
  fun `should find date among noise`() {
    val date = "April 1, 2018"
    val fullDate = "Saturday, $date"
    val test = "Lorem ipsum dolor :$fullDate<> as DateGif"

    Assert.assertEquals(date, parser.findDate(test))
  }

  @Test
  fun `should not find date when invalid day of week`() {
    val date = "April 1, 2018"
    val fullDate = "Fredas, $date"

    exception.expect(IllegalArgumentException::class.java)
    parser.findDate(fullDate)
  }

  @Test
  fun `should not find date when invalid day of month`() {
    val date = "April 82, 2018"
    val fullDate = "Monday, $date"

    exception.expect(IllegalArgumentException::class.java)
    parser.findDate(fullDate)
  }

  @Test
  fun `should not find date when invalid month`() {
    val date = "Kwiecien 12, 2018"
    val fullDate = "Monday, $date"

    exception.expect(IllegalArgumentException::class.java)
    parser.findDate(fullDate)
  }

  @Test
  fun `should not find date when invalid year`() {
    val date = "April 12, 201"
    val fullDate = "Monday, $date"

    exception.expect(IllegalArgumentException::class.java)
    parser.findDate(fullDate)
  }

  @Test
  fun `should parse one-digit date`() {
    val test = "Monday, April 5, 2018"

    val expected = Date(118,3,5)
    Assert.assertEquals(expected, parser.parseDate(test))
  }

  @Test
  fun `should parse two-digit date`() {
    val test = "Monday, September 25, 2018"

    val expected = Date(118,8,25)
    Assert.assertEquals(expected, parser.parseDate(test))
  }

  @Test
  fun `should parse date from noise`() {
    val date = "Saturday, June 1, 2018"
    val test = "Lorem ipsum dolor :$date<> as DateGif"

    val expected = Date(118,5,1)
    Assert.assertEquals(expected, parser.parseDate(test))
  }
}