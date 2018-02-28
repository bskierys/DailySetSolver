package pl.ipebk.setsolver.ui.solver

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.LinearLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_solver.*
import pl.ipebk.setsolver.R
import pl.ipebk.setsolver.domain.*
import pl.ipebk.setsolver.remote.*
import pl.ipebk.setsolver.remote.mapper.DailySetEntityMapper
import pl.ipebk.solver.Card
import pl.ipebk.solver.Set
import pl.ipebk.solver.SetGameSolver
import timber.log.Timber

class SolverActivity : AppCompatActivity() {
  private val remote = DailySetRemoteImpl(DailySetApiService(), DailySetEntityMapper())
  private val solver = SetGameSolver(4, 3)
  private val subscriptions = CompositeDisposable()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_solver)

    val downloadSubscription = remote.getPuzzleCards()
      .subscribeOn(Schedulers.io())
      .map { it.map { translateToSolverCard(it) } }
      .doOnSuccess {
        var log = ""
        it.forEach {
          log += it.toString()
          log += " "
        }
        Timber.d(log)
      }
      .map { solver.findSolution(it) }
      .observeOn(AndroidSchedulers.mainThread())
      .doOnSuccess { addSetsToLayout(it) }
      .doOnError { Timber.e(it) }
      .subscribe()

    subscriptions.add(downloadSubscription)
  }

  // todo write special mapper
  private fun translateToSolverCard(setCard: SetCard): Card {
    return Card(
      setCard.shading.ordinal,
      setCard.symbol.ordinal,
      setCard.color.ordinal,
      setCard.count.ordinal
    )
  }

  private fun translateFromSolverCard(solverCard: Card?): SetCard? {
    return SetCard(
      Shading.values()[solverCard!!.features[0].value],
      Symbol.values()[solverCard.features[1].value],
      Color.values()[solverCard.features[2].value],
      Count.values()[solverCard.features[3].value]
    )
  }

  private fun addSetsToLayout(sets: List<Set>) {
    sets.forEach {
      container.addView(getCardRow(it))
    }
  }

  private fun getCardRow(set: Set): LinearLayout {
    val linear = LinearLayout(this)
    linear.orientation = LinearLayout.HORIZONTAL
    set.cards.map { translateFromSolverCard(it) }
      .map { getCardImageView(it!!) }
      .forEach {
        linear.addView(it)
      }
    return linear
  }

  private fun getCardImageView(card: SetCard): ImageView {
    val image = ImageView(this)
    val cardName =
      "${card.shading.toString().toLowerCase()}_" +
        "${card.symbol.toString().toLowerCase()}_" +
        "${card.color.toString().toLowerCase()}_" +
        card.count.toString().toLowerCase()
    val cardResId = resources.getIdentifier(cardName, "drawable", packageName)
    image.setImageResource(cardResId)

    if(cardResId == 0) throw IllegalArgumentException("card: $cardName not found")

    return image
  }

  override fun onDestroy() {
    super.onDestroy()
    subscriptions.dispose()
  }
}
