package pl.ipebk.setsolver.ui.solver

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.LinearLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_solver.*
import pl.ipebk.setsolver.JobExecutor
import pl.ipebk.setsolver.R
import pl.ipebk.setsolver.UiThread
import pl.ipebk.setsolver.domain.SetCard
import pl.ipebk.setsolver.domain.SetCardThreePack
import pl.ipebk.setsolver.domain.SetSolution
import pl.ipebk.setsolver.domain.interactor.FindDailySetSolution
import pl.ipebk.setsolver.engine.CardMapperImpl
import pl.ipebk.setsolver.engine.DailySetEngineImpl
import pl.ipebk.setsolver.remote.DailySetApiService
import pl.ipebk.setsolver.remote.DailySetRemoteImpl
import pl.ipebk.setsolver.remote.mapper.DailySetEntityMapper
import pl.ipebk.solver.SetGameSolver
import timber.log.Timber

class SolverActivity : AppCompatActivity() {
  private val remote = DailySetRemoteImpl(DailySetApiService(), DailySetEntityMapper())
  private val engine = DailySetEngineImpl(SetGameSolver(4, 3), CardMapperImpl())

  private val findSolutionUseCase = FindDailySetSolution(engine, remote, JobExecutor(), UiThread())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_solver)

    findSolutionUseCase.execute(DailyPuzzleSubscriber())
  }

  override fun onDestroy() {
    super.onDestroy()
    findSolutionUseCase.dispose()
  }

  private fun addSetsToLayout(solution: SetSolution) {
    solution.sets.forEach {
      container.addView(getCardRow(it))
    }
  }

  private fun getCardRow(pack: SetCardThreePack): LinearLayout {
    val linear = LinearLayout(this)
    linear.orientation = LinearLayout.HORIZONTAL
    linear.addView(getCardImageView(pack.card1))
    linear.addView(getCardImageView(pack.card2))
    linear.addView(getCardImageView(pack.card3))
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

    if (cardResId == 0) throw IllegalArgumentException("card: $cardName not found")

    return image
  }

  inner class DailyPuzzleSubscriber: DisposableSingleObserver<SetSolution>() {
    override fun onSuccess(sets: SetSolution) {
      addSetsToLayout(sets)
    }

    override fun onError(e: Throwable) {
      // TODO: handle error on ui
    }
  }
}
