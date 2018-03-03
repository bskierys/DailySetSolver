package pl.ipebk.setsolver.remote.mapper

import org.junit.Assert
import org.junit.Test
import pl.ipebk.setsolver.domain.SetCard

class SetCardGeneratorTest {
  @Test
  fun `should generate all the cards`() {
    val cards = arrayListOf<SetCard>()

    SetCardGenerator().forEach {
      cards.add(it)
    }

    Assert.assertEquals(81, cards.size)
  }

  @Test
  fun `should generate all the cards whn not using forEach`() {
    val cards = arrayListOf<SetCard>()

    val generator = SetCardGenerator()
    while (generator.hasNext()) {
      cards.add(generator.next())
    }

    Assert.assertEquals(81, cards.size)
  }
}