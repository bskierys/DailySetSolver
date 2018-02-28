package pl.ipebk.setsolver.remote

import org.jsoup.nodes.Document
import java.util.regex.Pattern

/**
 * Parser to get entities from downloaded [Document]. This parser is strictly connected to jsoup
 * library.
 */
internal class SetResponseParser(private val document: Document) {
  private companion object {
    const val IMAGE_BASE_URL = "/images/setcards/small/"
    const val IMAGE_EXTENSION = ".gif"
    const val CARD_NAME_REGEX = "(.*)($IMAGE_BASE_URL)([0-9]{2})($IMAGE_EXTENSION)"
    val FIND_NUMBER_PATTERN: Pattern = Pattern.compile("\\d+")
  }

  fun parse(): List<Int> {
    val cardNumbers = ArrayList<Int>()
    val images = document.select("[src]")

    val cardImageNames = images
      .map { it.attr("src") }
      .filter { it.matches(Regex(CARD_NAME_REGEX)) }

    cardImageNames.forEach {
      val matcher = FIND_NUMBER_PATTERN.matcher(it)
      if(matcher.find()) {
        cardNumbers.add(matcher.group().toInt())
      }
    }

    return cardNumbers
  }
}