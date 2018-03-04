package pl.ipebk.setsolver

import dagger.Component
import pl.ipebk.setsolver.data.network.NetworkModule
import pl.ipebk.setsolver.ui.solver.SolverComponent
import pl.ipebk.setsolver.ui.solver.SolverModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
  ApplicationModule::class,
  NetworkModule::class
))
interface ApplicationComponent {

  // Injectors
  fun injectTo(app: KotlinBoilerplateApp)

  // Submodule methods
  // Every screen is its own submodule of the graph and must be added here.
  fun plus(module: SolverModule): SolverComponent
}