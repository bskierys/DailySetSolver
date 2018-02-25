package pl.ipebk.setsolver

import dagger.Component
import pl.ipebk.setsolver.data.network.NetworkModule
import pl.ipebk.setsolver.data.remote.ApiModule
import pl.ipebk.setsolver.ui.detail.DetailComponent
import pl.ipebk.setsolver.ui.detail.DetailModule
import pl.ipebk.setsolver.ui.list.ListComponent
import pl.ipebk.setsolver.ui.list.ListModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        NetworkModule::class,
        ApiModule::class
))
interface ApplicationComponent {

    // Injectors
    fun injectTo(app: KotlinBoilerplateApp)

    // Submodule methods
    // Every screen is its own submodule of the graph and must be added here.
    fun plus(module: ListModule): ListComponent
    fun plus(module: DetailModule): DetailComponent
}