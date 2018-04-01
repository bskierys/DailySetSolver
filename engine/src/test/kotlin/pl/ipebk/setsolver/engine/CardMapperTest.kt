package pl.ipebk.setsolver.engine

import org.junit.Assert
import org.junit.Test
import pl.ipebk.setsolver.domain.*
import pl.ipebk.solver.Card

class CardMapperTest {
  private val mapper = CardMapper()

  @Test
  fun `should properly map to solver model 1`() {
    val domainCard = SetCard(Shading.STRIPED, Symbol.OVAL, Color.GREEN, Count.ONE)

    val expected = Card(1, 2, 2, 0)
    val actual = mapper.mapToSolverModel(domainCard)
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun `should properly map to solver model 2`() {
    val domainCard = SetCard(Shading.OUTLINED, Symbol.SQUIGGLE, Color.RED, Count.THREE)

    val expected = Card(2, 0, 0, 2)
    val actual = mapper.mapToSolverModel(domainCard)
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun `should properly map from solver model 1`() {
    val solverCard = Card(1, 2, 2, 0)

    val expected = SetCard(Shading.STRIPED, Symbol.OVAL, Color.GREEN, Count.ONE)
    val actual = mapper.mapFromSolverModel(solverCard)
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun `should properly map from solver model 2`() {
    val solverCard = Card(2, 0, 0, 2)

    val expected = SetCard(Shading.OUTLINED, Symbol.SQUIGGLE, Color.RED, Count.THREE)
    val actual = mapper.mapFromSolverModel(solverCard)
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun `should throw exception when mapping from card with invalid feature`() {
    val solverCard = Card(2, 0, 0, 4)

    try {
      mapper.mapFromSolverModel(solverCard)
      Assert.fail("exception expected")
    } catch (ex: IllegalArgumentException) {
      // exception expected
    }
  }

  @Test
  fun `should throw exception when mapping from card with too much of features`() {
    val solverCard = Card(2, 0, 0, 2, 1)

    try {
      mapper.mapFromSolverModel(solverCard)
      Assert.fail("exception expected")
    } catch (ex: IllegalArgumentException) {
      // exception expected
    }
  }

  @Test
  fun `should throw exception when mapping from card with too few of features`() {
    val solverCard = Card(2, 0, 1)

    try {
      mapper.mapFromSolverModel(solverCard)
      Assert.fail("exception expected")
    } catch (ex: IllegalArgumentException) {
      // exception expected
    }
  }
}