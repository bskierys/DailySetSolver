package pl.ipebk.setsolver.remote

import io.reactivex.Single
import pl.ipebk.setsolver.remote.mapper.CardNumberParser
import javax.inject.Inject

/**
 * Service to retrieve today's puzzle
 */
class DailySetApiService @Inject constructor() {
  /**
   * Retrieve today's puzzle from official Set website
   */
  internal fun getTodayPuzzle(): Single<SetPuzzleResponse> {
    return Single.create(
      JsoupSimpleOnSubscribe("http://www.puzzles.setgame.com/puzzle/set.htm"))
      .map { SetResponseParser(DateParser(), CardNumberParser()).parse(it) }
  }
}