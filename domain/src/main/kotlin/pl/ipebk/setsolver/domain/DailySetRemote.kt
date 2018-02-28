package pl.ipebk.setsolver.domain

import io.reactivex.Single

/**
 * Interface defining methods for downloading daily set puzzle cards. Should be implemented by
 * remote layer
 */
interface DailySetRemote {
  /**
   * Retrieve daily puzzle from remote
   */
  fun getPuzzleCards() : Single<List<SetCard>>
}