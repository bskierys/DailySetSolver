package pl.ipebk.setsolver.presentation.ui.solver

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_solver.*
import kotlinx.android.synthetic.main.error_solver_layout.view.*
import kotlinx.android.synthetic.main.solution_solver_layout.view.*
import pl.ipebk.setsolver.presentation.appDi.ApplicationComponent
import pl.ipebk.setsolver.R
import pl.ipebk.setsolver.databinding.ActivitySolverBinding
import pl.ipebk.setsolver.domain.SetSolution
import pl.ipebk.setsolver.presentation.ui.base.ViewModelActivity
import pl.ipebk.setsolver.presentation.ui.misc.SimpleDividerItemDecoration
import timber.log.Timber
import javax.inject.Inject

class SolverActivity : ViewModelActivity<SolverViewModel, ActivitySolverBinding>() {
  @Inject
  lateinit var adapter: SetAdapter

  @Inject
  lateinit var layoutManager: LinearLayoutManager

  @Inject
  lateinit var dividerDecorator: SimpleDividerItemDecoration

  private val disposables: CompositeDisposable = CompositeDisposable()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_solver)
  }

  override fun onBind() {
    super.onBind()
    binding.viewModel = viewModel

    error.visibility = View.INVISIBLE
    setupRecyclerView()

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

  private fun setupRecyclerView() {
    solution.listRecyclerView.adapter = adapter
    solution.listRecyclerView.layoutManager = layoutManager
    solution.listRecyclerView.addItemDecoration(dividerDecorator)
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

  private fun showSolutionLayout(setSolution: SetSolution) {
    error.visibility = View.INVISIBLE
    loading.visibility = View.INVISIBLE
    solution.visibility = View.VISIBLE

    adapter.updateSets(setSolution.sets)
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
}
