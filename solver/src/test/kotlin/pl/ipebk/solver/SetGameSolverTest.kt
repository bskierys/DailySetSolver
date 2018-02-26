package pl.ipebk.solver

import org.junit.Assert
import org.junit.Test

class SetGameSolverTest {
    @Test
    fun `shouldReturnEmptyList`() {
        val solver = SetGameSolver(4,3)
        val cards = ArrayList<Card>()
        Assert.assertTrue(solver.findSolution(cards).isEmpty())
    }
}