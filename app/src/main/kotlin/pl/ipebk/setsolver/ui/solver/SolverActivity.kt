package pl.ipebk.setsolver.ui.solver

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_solver.*
import pl.ipebk.setsolver.ApplicationComponent
import pl.ipebk.setsolver.R
import pl.ipebk.setsolver.databinding.ActivitySolverBinding
import pl.ipebk.setsolver.domain.SetCard
import pl.ipebk.setsolver.domain.SetCardThreePack
import pl.ipebk.setsolver.domain.SetSolution
import pl.ipebk.setsolver.ui.base.ViewModelActivity

class SolverActivity : ViewModelActivity<SolverViewModel, ActivitySolverBinding>() {
  private lateinit var container: LinearLayout
  private lateinit var errorMessage: TextView

  private val disposables: CompositeDisposable = CompositeDisposable()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_solver)
  }

  override fun onBind() {
    super.onBind()
    binding.viewModel = viewModel

    container = findViewById(R.id.container)
    errorMessage = findViewById(R.id.error_message)
    error.visibility = View.INVISIBLE

    viewModel.fetchAndSolveDaily()

    disposables.add(viewModel.solutionStream.subscribe {
      showSolutionLayout(it)
    })

    disposables.add(viewModel.loadingStateStream.subscribe {
      showLoadingState(it)
    })

    disposables.add(viewModel.genericErrorStream.subscribe {
      showErrorState(getString(R.string.solver_error_generic))
    })

    disposables.add(viewModel.networkErrorStream.subscribe {
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

    errorMessage.text = message
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
    container.removeAllViews()
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
}
