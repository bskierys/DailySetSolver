package pl.ipebk.setsolver.presentation.ui.solver

import dagger.Subcomponent
import pl.ipebk.setsolver.presentation.ui.ActivityScope

@ActivityScope
@Subcomponent(modules = arrayOf(
  SolverModule::class
))
interface SolverComponent {
  fun injectTo(activity: SolverActivity)
}