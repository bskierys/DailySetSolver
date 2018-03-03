package pl.ipebk.setsolver.remote.mapper

import pl.ipebk.setsolver.domain.*
import javax.inject.Inject

/**
 * Map a [Int] to a [SetCard] instance when data is moving between this layer and the Domain layer
 */
class DailySetEntityMapper @Inject constructor(): EntityMapper<Int, SetCard> {

  private object SetCardHolder {
    val cardMap = mutableMapOf<Int, SetCard>()

    /**
     * This is exactly how card are numbered on the server site. We will replicate this method
     * to ensure mapping is correct
     */
    init {
      var currentIndex = 0

      SetCardGenerator().forEach {
        cardMap[++ currentIndex] = it
      }
    }
  }

  override fun mapFromRemote(type: Int): SetCard {
    if (type <= 0 || type > SetCardHolder.cardMap.size)
      throw IllegalArgumentException("no such card")

    return SetCardHolder.cardMap[type]!!
  }
}