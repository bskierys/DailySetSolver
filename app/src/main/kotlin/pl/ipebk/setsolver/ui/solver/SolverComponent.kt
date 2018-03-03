package pl.ipebk.setsolver.ui.solver

import dagger.Subcomponent
import pl.ipebk.setsolver.ui.ActivityScope

@ActivityScope
@Subcomponent(modules = arrayOf(
  SolverModule::class
))
interface SolverComponent {
  fun injectTo(activity: SolverActivity)
}