package pl.ipebk.setsolver.presentation.appDi

import dagger.Component
import pl.ipebk.setsolver.presentation.DailySetSolverApp
import pl.ipebk.setsolver.presentation.data.network.NetworkModule
import pl.ipebk.setsolver.presentation.ui.solver.SolverComponent
import pl.ipebk.setsolver.presentation.ui.solver.SolverModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
  ApplicationModule::class,
  NetworkModule::class,
  DomainModule::class,
  Rx::class
])
interface ApplicationComponent {

  // Injectors
  fun injectTo(app: DailySetSolverApp)

  // Submodule methods
  // Every screen is its own submodule of the graph and must be added here.
  fun plus(module: SolverModule): SolverComponent
}