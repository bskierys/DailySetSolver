package pl.ipebk.setsolver.ui.detail

import dagger.Subcomponent
import pl.ipebk.setsolver.ui.ActivityScope

@ActivityScope
@Subcomponent(modules = arrayOf(
        DetailModule::class
))
interface DetailComponent {
    fun injectTo(activity: DetailActivity)
}