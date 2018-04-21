package pl.ipebk.setsolver.remote.mapper

import java.util.*
import java.util.regex.Pattern

/**
 * Designed to find card numbers in list of [String] - image names
 */
internal class CardNumberParser {

  private companion object {
    const val IMAGE_BASE_URL = "/images/setcards/small/"
    const val IMAGE_EXTENSION = ".gif"
    const val CARD_NAME_REGEX = "(.*)($IMAGE_BASE_URL)([0-9]{2})($IMAGE_EXTENSION)"
    val FIND_NUMBER_PATTERN: Pattern = Pattern.compile("\\d+")
  }

  /**
   * Filters out and parses card numbers from list of image names. To be found, card image name
   * must be /images/setcards/small/TWO_DIGIT_NUMBER.gif
   */
  fun parseCardNumbers(names: List<String>): List<Int> {
    val cardNumbers = ArrayList<Int>()

    val cardImageNames = names
      .filter { it.matches(Regex(CARD_NAME_REGEX)) }

    cardImageNames.forEach {
      val matcher = FIND_NUMBER_PATTERN.matcher(it)
      if (matcher.find()) {
        cardNumbers.add(matcher.group().toInt())
      }
    }

    return cardNumbers
  }
}