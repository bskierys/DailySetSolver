package pl.ipebk.setsolver.presentation.ui.solver

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import pl.ipebk.setsolver.R
import pl.ipebk.setsolver.domain.*
import pl.ipebk.setsolver.domain.Color
import javax.inject.Inject

/**
 * Provides images for cards that should be displayed on the screen
 */
class SetCardDrawableProvider @Inject constructor(private val context: Context) {
  private val resources = context.resources

  private val cardHeight = resources.getDimensionPixelSize(R.dimen.card_height)
  private val cardWidth = resources.getDimensionPixelSize(R.dimen.card_width)
  private val cardRadius = resources.getDimensionPixelSize(R.dimen.card_radius)
  private val cardShapesMargin = resources.getDimensionPixelSize(R.dimen.card_shapes_margin)
  private val backgroundColor = ContextCompat.getColor(context, R.color.set_card_bg)
  private val drawSpace : RectF

  private val shapeDrawables = HashMap<String, Bitmap>()

  private val canvas = Canvas()
  private var shapePaint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = backgroundColor }

  private var isInitialized = false

  init {
    drawSpace = RectF(
      cardShapesMargin.toFloat(),
      cardShapesMargin.toFloat(),
      cardWidth.toFloat() - cardShapesMargin,
      cardHeight.toFloat() - cardShapesMargin)
  }

  private fun initialize() {
    if(isInitialized) {
      return
    }

    val desiredHeight = drawSpace.height()

    shapeDrawables.apply {
      put(shapeIdentifierFor(Symbol.OVAL, Shading.SOLID),
        scaledBitmapFromVectorDrawable(R.drawable.sh_oval_solid, desiredHeight))
      put(shapeIdentifierFor(Symbol.OVAL, Shading.STRIPED),
        scaledBitmapFromVectorDrawable(R.drawable.sh_oval_striped, desiredHeight))
      put(shapeIdentifierFor(Symbol.OVAL, Shading.OUTLINED),
        scaledBitmapFromVectorDrawable(R.drawable.sh_oval_outlined, desiredHeight))
      put(shapeIdentifierFor(Symbol.DIAMOND, Shading.SOLID),
        scaledBitmapFromVectorDrawable(R.drawable.sh_diamond_solid, desiredHeight))
      put(shapeIdentifierFor(Symbol.DIAMOND, Shading.STRIPED),
        scaledBitmapFromVectorDrawable(R.drawable.sh_diamond_striped, desiredHeight))
      put(shapeIdentifierFor(Symbol.DIAMOND, Shading.OUTLINED),
        scaledBitmapFromVectorDrawable(R.drawable.sh_diamond_outlined, desiredHeight))
      put(shapeIdentifierFor(Symbol.SQUIGGLE, Shading.SOLID),
        scaledBitmapFromVectorDrawable(R.drawable.sh_squiggle_solid, desiredHeight))
      put(shapeIdentifierFor(Symbol.SQUIGGLE, Shading.STRIPED),
        scaledBitmapFromVectorDrawable(R.drawable.sh_squiggle_striped, desiredHeight))
      put(shapeIdentifierFor(Symbol.SQUIGGLE, Shading.OUTLINED),
        scaledBitmapFromVectorDrawable(R.drawable.sh_squiggle_outlined, desiredHeight))
    }

    isInitialized = true
  }

  private fun scaledBitmapFromVectorDrawable(id: Int, desiredHeight: Float): Bitmap {
    var drawable = ContextCompat.getDrawable(context, id)
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      drawable = DrawableCompat.wrap(drawable!!).mutate()
    }

    val factor = desiredHeight / drawable!!.intrinsicHeight

    val bitmap = Bitmap.createBitmap((drawable.intrinsicWidth * factor).toInt(),
      (drawable.intrinsicHeight * factor).toInt(), Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap
  }

  private fun shapeIdentifierFor(symbol: Symbol, shading: Shading): String {
    return symbol.toString() + "_" + shading.toString()
  }

  /**
   * @param card Instance of [SetCard] to match image for
   * @return [Drawable] to represent card on the screen
   */
  fun provideCardDrawable(card: SetCard): Drawable? {
    if(!isInitialized) {
      initialize()
    }

    val bitmap = Bitmap.createBitmap(cardWidth, cardHeight, Bitmap.Config.ARGB_8888)
    val c = canvas
    c.setBitmap(bitmap)
    drawBackground(c)
    drawShapes(c, card)

    return BitmapDrawable(resources, bitmap)
  }

  private fun drawBackground(canvas: Canvas) {
    val cardRect = RectF(0f, 0f, cardWidth.toFloat(), cardHeight.toFloat())
    canvas.drawRoundRect(cardRect, cardRadius.toFloat(), cardRadius.toFloat(), backgroundPaint)
  }

  private fun drawShapes(canvas: Canvas, card: SetCard) {
    val shape = shapeDrawables[shapeIdentifierFor(card.symbol, card.shading)]

    val color = when(card.color) {
      Color.RED -> ContextCompat.getColor(context, R.color.set_shape_red)
      Color.GREEN -> ContextCompat.getColor(context, R.color.set_shape_green)
      Color.PURPLE -> ContextCompat.getColor(context, R.color.set_shape_purple)
    }

    val shapeColorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
    shapePaint.color = color
    shapePaint.colorFilter = shapeColorFilter

    when (card.count) {
      Count.ONE -> drawOneShape(shape!!, drawSpace, canvas)
      Count.TWO -> drawTwoShapes(shape!!, drawSpace, canvas)
      Count.THREE -> drawThreeShapes(shape!!, drawSpace, canvas)
    }
  }

  private fun drawOneShape(shape: Bitmap, space: RectF, canvas: Canvas) {
    val leftOfShape = space.centerX() - shape.width.toFloat() / 2
    canvas.drawBitmap(shape, leftOfShape, space.top, shapePaint)
  }

  private fun drawTwoShapes(shape: Bitmap, space: RectF, canvas: Canvas) {
    val fraction = .6f
    val halfWidth = space.width() / 2
    val leftOfShape1 = space.left + fraction * halfWidth - shape.width.toFloat() / 2
    val leftOfShape2 = space.centerX() + (1 - fraction) * halfWidth - shape.width.toFloat() / 2
    canvas.drawBitmap(shape, leftOfShape1, space.top, shapePaint)
    canvas.drawBitmap(shape, leftOfShape2, space.top, shapePaint)
  }

  private fun drawThreeShapes(shape: Bitmap, space: RectF, canvas: Canvas) {
    val leftOfShape1 = space.left
    val leftOfShape2 = space.centerX() - shape.width.toFloat() / 2
    val leftOfShape3 = space.right - shape.width
    canvas.drawBitmap(shape, leftOfShape1, space.top, shapePaint)
    canvas.drawBitmap(shape, leftOfShape2, space.top, shapePaint)
    canvas.drawBitmap(shape, leftOfShape3, space.top, shapePaint)
  }
}