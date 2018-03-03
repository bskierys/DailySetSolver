package pl.ipebk.setsolver.remote

import io.reactivex.Single
import javax.inject.Inject

/**
 * Service to retrieve today's puzzle
 */
class DailySetApiService @Inject constructor() {
  /**
   * Retrieve today's puzzle from official Set website
   */
  fun getTodayPuzzle(): Single<List<Int>> {
    return Single.create(
      JsoupSimpleOnSubscribe("http://www.puzzles.setgame.com/puzzle/set.htm"))
      .map { SetResponseParser(it).parse() }
  }
}