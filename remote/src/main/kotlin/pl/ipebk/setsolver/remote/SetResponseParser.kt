package pl.ipebk.setsolver.remote

import org.jsoup.nodes.Document
import pl.ipebk.setsolver.remote.mapper.CardNumberParser
import java.util.*
import java.util.regex.Pattern

/**
 * Parser to get entities from downloaded [Document]. This parser is strictly connected to jsoup
 * library.
 */
internal class SetResponseParser(private val dateParser: DateParser,
                                 private val cardNumberParser: CardNumberParser) {

  fun parse(document: Document): SetPuzzleResponse {
    val images = document
      .select("[src]")
      .map { it.attr("src") }

    val cardNumbers = cardNumberParser.parseCardNumbers(images)
    val date = dateParser.parseDate(document.body().html())

    return SetPuzzleResponse(date, cardNumbers)
  }
}