package pl.ipebk.solver

import org.junit.Assert
import org.junit.Test

class SetGameSolverTest {
    @Test
    fun `should throw exception when finding solution for empty list`() {
        val solver = SetGameSolver(4,3)
        val cards = ArrayList<Card>()
        try {
            solver.findSolution(cards)
            Assert.fail("should throw exception")
        } catch (ex: IllegalArgumentException) {
            // expected exception
        }
    }

    @Test
    fun `should throw exception when finding solution for fewer cards than feature variations`() {
        val solver = SetGameSolver(2,3)
        val cards = ArrayList<Card>()
        cards.add(Card(1,1))
        cards.add(Card(0,2))
        try {
            solver.findSolution(cards)
            Assert.fail("should throw exception")
        } catch (ex: IllegalArgumentException) {
            // expected exception
        }
    }

    @Test
    fun `should throw exception when feature variants out of range`() {
        val solver = SetGameSolver(2,3)
        val cards = ArrayList<Card>()
        cards.add(Card(1,2))
        cards.add(Card(2,2))
        cards.add(Card(0,3))
        try {
            solver.findSolution(cards)
            Assert.fail("should throw exception")
        } catch (ex: IllegalArgumentException) {
            // expected exception
        }
    }

    @Test
    fun `should throw exception when feature variants out of range 2`() {
        val solver = SetGameSolver(2,3)
        val cards = ArrayList<Card>()
        cards.add(Card(1,2))
        cards.add(Card(2,2))
        cards.add(Card(-5,2))
        try {
            solver.findSolution(cards)
            Assert.fail("should throw exception")
        } catch (ex: IllegalArgumentException) {
            // expected exception
        }
    }

    @Test
    fun `should throw exception when number of features too low`() {
        val solver = SetGameSolver(2,3)
        val cards = ArrayList<Card>()
        cards.add(Card(1))
        cards.add(Card(2,2))
        cards.add(Card(0,1))
        try {
            solver.findSolution(cards)
            Assert.fail("should throw exception")
        } catch (ex: IllegalArgumentException) {
            // expected exception
        }
    }

    @Test
    fun `should throw exception when number of features too high`() {
        val solver = SetGameSolver(2,3)
        val cards = ArrayList<Card>()
        cards.add(Card(1,2, 2))
        cards.add(Card(2,2))
        cards.add(Card(0,2))
        try {
            solver.findSolution(cards)
            Assert.fail("should throw exception")
        } catch (ex: IllegalArgumentException) {
            // expected exception
        }
    }

    @Test
    fun `should throw exception when two cards identical`() {
        val solver = SetGameSolver(2,3)
        val cards = ArrayList<Card>()
        cards.add(Card(1,2))
        cards.add(Card(1,2))
        cards.add(Card(2,2))
        try {
            solver.findSolution(cards)
            Assert.fail("should throw exception")
        } catch (ex: IllegalArgumentException) {
            // expected exception
        }
    }

    /**
     * This test is actual daily sets challenge
     */
    @Test
    fun `should find six sets in standard game`() {
        val solver = SetGameSolver(4,3)
        val cards = constructDailySetChallenge()
        val solution = solver.findSolution(cards)
        Assert.assertEquals(6, solution.size)
    }

    private fun constructDailySetChallenge() : List<Card> {
        val cards = ArrayList<Card>()
        cards.add(Card(0,0,1,2))
        cards.add(Card(1,2,1,0))
        cards.add(Card(0,1,2,2))
        cards.add(Card(0,2,0,2))
        cards.add(Card(2,1,1,0))
        cards.add(Card(2,2,2,1))
        cards.add(Card(2,1,0,2))
        cards.add(Card(1,2,2,2))
        cards.add(Card(0,0,0,1))
        cards.add(Card(1,1,1,1))
        cards.add(Card(1,0,0,2))
        cards.add(Card(0,2,0,0))
        return cards
    }
}