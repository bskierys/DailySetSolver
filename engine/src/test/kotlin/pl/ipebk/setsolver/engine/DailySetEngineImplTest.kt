package pl.ipebk.setsolver.engine

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import pl.ipebk.setsolver.domain.*
import pl.ipebk.solver.Card
import pl.ipebk.solver.Set
import pl.ipebk.solver.SetGameSolver

class DailySetEngineImplTest {
  @Mock
  private lateinit var mapper: CardMapper
  @Mock
  private lateinit var solver: SetGameSolver

  private lateinit var engine: DailySetEngineImpl

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)
    engine = DailySetEngineImpl(solver, mapper)

    val solverCard = generateSimpleSolverCard()
    val domainCard = generateSimpleDomainCard()
    Mockito.`when`(mapper.mapToSolverModel(any()))
      .thenReturn(solverCard)
    Mockito.`when`(mapper.mapFromSolverModel(any()))
      .thenReturn(domainCard)
  }

  private fun generateSimpleSolverCard(): Card {
    return Card(1, 2, 3)
  }

  private fun generateSimpleDomainCard(): SetCard {
    return SetCard(Shading.STRIPED, Symbol.OVAL, Color.GREEN, Count.ONE)
  }

  @Test
  fun `should solve puzzle and map properly`() {
    val solverCard = generateSimpleSolverCard()
    Mockito.`when`(solver.findSolution(any())).thenReturn(arrayListOf(
      Set(arrayListOf(solverCard, solverCard, solverCard))
    ))

    val cards = arrayListOf(generateSimpleDomainCard())

    val testObserver = engine.getSolution(cards).test()
    testObserver
      .assertComplete()
      .assertValue { it.sets.isNotEmpty() }
    Mockito.verify(solver).findSolution(any())
  }

  @Test
  fun `should throw exception when too few cards`() {
    val solverCard = generateSimpleSolverCard()
    Mockito.`when`(solver.findSolution(any())).thenReturn(arrayListOf(
      Set(arrayListOf(solverCard, solverCard))
    ))

    val cards = arrayListOf(generateSimpleDomainCard())

    val testObserver = engine.getSolution(cards).test()
    testObserver.assertError(ArrayIndexOutOfBoundsException::class.java)
    Mockito.verify(solver).findSolution(any())
  }

  private fun <T> any(): T {
    Mockito.any<T>()
    return uninitialized()
  }

  private fun <T> uninitialized(): T = null as T
}