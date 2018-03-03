package pl.ipebk.setsolver.remote

import org.junit.Assert
import org.junit.Test
import pl.ipebk.setsolver.domain.*
import pl.ipebk.setsolver.remote.mapper.DailySetEntityMapper

class DailySetEntityMapperTest {
  private val mapper = DailySetEntityMapper()

  companion object {
    private val card1 = SetCard(Shading.SOLID, Symbol.SQUIGGLE, Color.RED, Count.ONE)
    private val card24 = SetCard(Shading.SOLID, Symbol.OVAL, Color.PURPLE, Count.THREE)
    private val card32 = SetCard(Shading.STRIPED, Symbol.SQUIGGLE, Color.PURPLE, Count.TWO)
    private val card73 = SetCard(Shading.OUTLINED, Symbol.OVAL, Color.RED, Count.ONE)
    private val card52 = SetCard(Shading.STRIPED, Symbol.OVAL, Color.GREEN, Count.ONE)
    private val card81 = SetCard(Shading.OUTLINED, Symbol.OVAL, Color.GREEN, Count.THREE)
  }

  @Test
  fun `should map card1 properly`() {
    Assert.assertEquals(card1, mapper.mapFromRemote(1))
  }

  @Test
  fun `should map card24 properly`() {
    Assert.assertEquals(card24, mapper.mapFromRemote(24))
  }

  @Test
  fun `should map card32 properly`() {
    Assert.assertEquals(card32, mapper.mapFromRemote(32))
  }

  @Test
  fun `should map card73 properly`() {
    Assert.assertEquals(card73, mapper.mapFromRemote(73))
  }

  @Test
  fun `should map card52 properly`() {
    Assert.assertEquals(card52, mapper.mapFromRemote(52))
  }

  @Test
  fun `should map card81 properly`() {
    Assert.assertEquals(card81, mapper.mapFromRemote(81))
  }

  @Test
  fun `should throw exception when card number too high`() {
    try {
      mapper.mapFromRemote(92)
      Assert.fail("exception expected")
    } catch (ex: IllegalArgumentException) {
      //exception expected
    }
  }

  @Test
  fun `should throw exception when card number too low`() {
    try {
      mapper.mapFromRemote(0)
      Assert.fail("exception expected")
    } catch (ex: IllegalArgumentException) {
      //exception expected
    }
  }
}