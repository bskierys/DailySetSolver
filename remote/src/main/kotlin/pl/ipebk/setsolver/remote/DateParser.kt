package pl.ipebk.setsolver.remote

import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * Designed to find date in a String
 */
internal class DateParser {

  private companion object {
    const val DAY_OF_WEEK = "Sunday|Monday|Tuesday|Wednesday|Thursday|Friday|Saturday"
    const val MONTH =
      "December|January|February|" +
        "March|April|May|" +
        "June|July|August|" +
        "September|October|November"

    const val DAY_OF_MONTH = "[1-9]|1[0-9]|2[0-9]|3[0-1]"
    const val DATE_REGEX = "($DAY_OF_WEEK), ($MONTH) ($DAY_OF_MONTH), (\\d{4})"
    val FIND_DATE_PATTERN: Pattern = Pattern.compile(DATE_REGEX)
    val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
  }

  /**
   * Parses [Date] from [String]. The date must be in format 'DAY_OF_WEEK MONTH DAY_OF_MONTH,
   * YEAR'. A source string must contain only one date of that format.
   *
   * @throws IllegalArgumentException when source string contains no date of said format or
   * contains more than one
   */
  fun parseDate(source: String): Date {
    val dateRepresentation = findDate(source)
    return dateFormat.parse(dateRepresentation)
  }

  internal fun findDate(source: String): String {
    val dates = arrayListOf<String>()
    val matcher = FIND_DATE_PATTERN.matcher(source)
    while (matcher.find()) {
      val formatDate = "${matcher.group(2)} ${matcher.group(3)}, ${matcher.group(4)}"
      dates.add(formatDate)
    }

    if (dates.size == 0) {
      throw IllegalArgumentException("no dates included in the source")
    }

    if (dates.size > 1) {
      throw IllegalArgumentException("more than one date found in the source")
    }

    return dates[0]
  }
}