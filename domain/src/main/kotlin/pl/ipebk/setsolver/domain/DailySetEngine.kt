package pl.ipebk.setsolver.domain

import io.reactivex.Single

/**
 * Interface defining methods for solving set puzzle. Should be implemented by engine layer
 */
interface DailySetEngine {
  /**
   * Find sets within given cards
   */
  fun getSolution(puzzle: SetPuzzle) : Single<SetSolution>
}