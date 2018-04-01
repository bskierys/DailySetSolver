package pl.ipebk.setsolver.presentation

import android.app.Application
import dagger.Lazy
import pl.ipebk.setsolver.BuildConfig
import pl.ipebk.setsolver.presentation.appDi.ApplicationComponent
import pl.ipebk.setsolver.presentation.appDi.ApplicationModule
import pl.ipebk.setsolver.presentation.appDi.DaggerApplicationComponent
import timber.log.Timber
import javax.inject.Inject

class DailySetSolverApp : Application() {

  @Inject
  lateinit var debugTree: Lazy<Timber.DebugTree>

  companion object {
    lateinit var graph: ApplicationComponent
  }

  override fun onCreate() {
    super.onCreate()

    initDependencyGraph()

    if (BuildConfig.DEBUG) {
      Timber.plant(debugTree.get())
    }
  }

  private fun initDependencyGraph() {
    graph = DaggerApplicationComponent.builder()
      .applicationModule(ApplicationModule(this))
      .build()
    graph.injectTo(this)
  }
}