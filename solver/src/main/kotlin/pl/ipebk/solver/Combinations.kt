package pl.ipebk.solver

/**
 * Computes all k-element combinations of n-element set. n-element set is fixed to be 0,...,n-1 so
 * returned values will be indexes of array you want to finds subsets of
 */
internal class Combinations(private val k: Int, private val n: Int) {
  val computed = ArrayList<ArrayList<Int>>()
  private var temp = Array(k, { 0 })

  init {
    if (k > n) throw IllegalArgumentException("subsets greater than whole sets")
    computeStartingWith(1, k)
  }

  private fun computeStartingWith(i: Int, p: Int) {
    for (j in i..n - p + 1 step 1) {
      temp[k - p] = j - 1
      if (p == 1) {
        val newCombination = ArrayList<Int>()
        newCombination += temp
        computed.add(newCombination)
      } else {
        computeStartingWith(j + 1, p - 1)
      }
    }
  }
}