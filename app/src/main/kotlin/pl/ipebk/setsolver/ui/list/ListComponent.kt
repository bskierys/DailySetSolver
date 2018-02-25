package pl.ipebk.setsolver.ui.list

import dagger.Subcomponent
import pl.ipebk.setsolver.ui.ActivityScope

@ActivityScope
@Subcomponent(modules = arrayOf(
        ListModule::class
))
interface ListComponent {

    fun injectTo(activity: ListActivity)
}