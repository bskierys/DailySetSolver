package pl.ipebk.setsolver.presentation.ui.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import pl.ipebk.setsolver.presentation.appDi.ApplicationComponent
import pl.ipebk.setsolver.presentation.DailySetSolverApp

abstract class BaseActivity : AppCompatActivity() {

  @CallSuper
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    injectDependencies(DailySetSolverApp.graph)
  }

  abstract fun injectDependencies(graph: ApplicationComponent)
}