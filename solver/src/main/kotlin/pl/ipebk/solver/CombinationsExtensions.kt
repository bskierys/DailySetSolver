package pl.ipebk.solver

/**
 * Returns list of subsets of all elements of lists (combination). None of the elements will be
 * put into one subset twice.
 *
 * @param numberOfElements number of elements in all subsets
 */
fun <T> ArrayList<T>.allCombinations(numberOfElements: Int): ArrayList<ArrayList<T>> {
  val allCombinations = ArrayList<ArrayList<T>>()

  val indexCombinations = Combinations(numberOfElements, this.size).computed
  for (indexCombination in indexCombinations) {
    val combination = ArrayList<T>()
    for (index in indexCombination) {
      combination.add(this[index])
    }
    allCombinations.add(combination)
  }

  return allCombinations
}