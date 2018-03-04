package pl.ipebk.setsolver.presentation.ui.solver

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import pl.ipebk.setsolver.domain.SetCard
import javax.inject.Inject

/**
 * Provides images for cards that should be displayed on the screen
 */
class SetCardDrawableProvider @Inject constructor(private val context: Context) {

  /**
   * @param card Instance of [SetCard] to match image for
   * @return [Drawable] to represent card on the screen
   */
  fun provideCardDrawable(card: SetCard): Drawable? {
    val cardName =
      "${card.shading.toString().toLowerCase()}_" +
        "${card.symbol.toString().toLowerCase()}_" +
        "${card.color.toString().toLowerCase()}_" +
        card.count.toString().toLowerCase()

    val cardResId = context.resources.getIdentifier(cardName, "drawable", context.packageName)
    if (cardResId == 0) throw IllegalArgumentException("card: $cardName not found")

    return ContextCompat.getDrawable(context, cardResId)
  }
}