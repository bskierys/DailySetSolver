package pl.ipebk.setsolver.remote

import org.junit.Assert
import org.junit.Test
import pl.ipebk.setsolver.domain.*
import pl.ipebk.setsolver.remote.mapper.DailySetEntityMapper

class DailySetEntityMapperTest {
  private val mapper = DailySetEntityMapper()

  companion object {
    private val card24 = SetCard(Shading.SOLID, Symbol.OVAL, Color.PURPLE, Count.THREE)
    private val card32 = SetCard(Shading.STRIPED, Symbol.SQUIGGLE, Color.PURPLE, Count.TWO)
    private val card73 = SetCard(Shading.OUTLINED, Symbol.OVAL, Color.RED, Count.ONE)
    private val card52 = SetCard(Shading.STRIPED, Symbol.OVAL, Color.GREEN, Count.ONE)
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
}