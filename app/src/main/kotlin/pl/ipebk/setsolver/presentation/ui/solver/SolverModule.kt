package pl.ipebk.setsolver.presentation.ui.solver

import android.content.Context
import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import pl.ipebk.setsolver.presentation.ui.base.ActivityModule

@Module
class SolverModule(activity: AppCompatActivity) : ActivityModule(activity) {

  @Provides
  fun provideResources(context: Context): Resources = context.resources

  @Provides
  fun provideLinearLayoutManager(context: Context): LinearLayoutManager =
    LinearLayoutManager(context)
}