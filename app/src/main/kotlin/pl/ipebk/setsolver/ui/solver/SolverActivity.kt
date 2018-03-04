package pl.ipebk.setsolver.ui.solver

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_solver.*
import kotlinx.android.synthetic.main.error_solver_layout.view.*
import kotlinx.android.synthetic.main.solution_solver_layout.view.*
import pl.ipebk.setsolver.ApplicationComponent
import pl.ipebk.setsolver.R
import pl.ipebk.setsolver.databinding.ActivitySolverBinding
import pl.ipebk.setsolver.domain.SetCard
import pl.ipebk.setsolver.domain.SetCardThreePack
import pl.ipebk.setsolver.domain.SetSolution
import pl.ipebk.setsolver.ui.base.ViewModelActivity
import timber.log.Timber

class SolverActivity : ViewModelActivity<SolverViewModel, ActivitySolverBinding>() {
  private val disposables: CompositeDisposable = CompositeDisposable()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_solver)
  }

  override fun onBind() {
    super.onBind()
    binding.viewModel = viewModel

    error.visibility = View.INVISIBLE

    viewModel.fetchAndSolveDaily()

    disposables.add(viewModel.solutionStream
      .doOnError(Timber::e)
      .subscribe {
        showSolutionLayout(it)
      })

    disposables.add(viewModel.loadingStateStream
      .doOnError(Timber::e)
      .subscribe {
        showLoadingState(it)
      })

    disposables.add(viewModel.genericErrorStream
      .doOnError(Timber::e)
      .subscribe {
        showErrorState(getString(R.string.solver_error_generic))
      })

    disposables.add(viewModel.networkErrorStream
      .doOnError(Timber::e)
      .subscribe {
        showErrorState(getString(R.string.solver_error_network))
      })
  }

  private fun showLoadingState(visible: Boolean) {
    if (visible) {
      loading.visibility = View.VISIBLE
      error.visibility = View.INVISIBLE
      solution.visibility = View.INVISIBLE
    } else {
      loading.visibility = View.INVISIBLE
    }
  }

  private fun showSolutionLayout(sets: SetSolution) {
    error.visibility = View.INVISIBLE
    loading.visibility = View.INVISIBLE
    solution.visibility = View.VISIBLE

    addSetsToLayout(sets)
  }

  private fun showErrorState(message: String) {
    error.visibility = View.VISIBLE
    loading.visibility = View.INVISIBLE
    solution.visibility = View.INVISIBLE

    error.error_message.text = message
  }

  override fun onDestroy() {
    super.onDestroy()
    disposables.dispose()
  }

  override fun injectDependencies(graph: ApplicationComponent) {
    graph.plus(SolverModule(this)).injectTo(this)
  }

  override fun getViewBinding(): ActivitySolverBinding {
    return DataBindingUtil.setContentView(this, R.layout.activity_solver)
  }

  private fun addSetsToLayout(solution: SetSolution) {
    this.solution.container.removeAllViews()
    solution.sets.forEach {
      this.solution.container.addView(getCardRow(it))
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
}
