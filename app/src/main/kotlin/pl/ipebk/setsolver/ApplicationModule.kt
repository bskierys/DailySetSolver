package pl.ipebk.setsolver

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import com.github.bskierys.pine.Pine
import dagger.Module
import dagger.Provides
import pl.ipebk.setsolver.domain.executor.PostExecutionThread
import pl.ipebk.setsolver.domain.executor.ThreadExecutor
import pl.ipebk.setsolver.domain.interactor.FindDailySetSolution
import pl.ipebk.setsolver.engine.CardMapperImpl
import pl.ipebk.setsolver.engine.DailySetEngineImpl
import pl.ipebk.setsolver.remote.DailySetApiService
import pl.ipebk.setsolver.remote.DailySetRemoteImpl
import pl.ipebk.setsolver.remote.mapper.DailySetEntityMapper
import pl.ipebk.solver.SetGameSolver
import timber.log.Timber
import javax.inject.Singleton

@Module
class ApplicationModule(private val app: KotlinBoilerplateApp) {

  @Provides
  @Singleton
  fun provideApplication(): Application = app

  @Provides
  @Singleton
  @ApplicationQualifier
  fun provideContext(): Context = app.baseContext

  @Provides
  @Singleton
  fun provideResources(): Resources = app.resources

  @Provides
  @Singleton
  fun provideLayoutInflater(@ApplicationQualifier context: Context): LayoutInflater {
    return LayoutInflater.from(context)
  }

  @Provides
  fun provideDebugTree(): Timber.DebugTree {
    return Pine.Builder()
      .addPackageReplacePattern(app.packageName, "SETAPP")
      .grow()
  }

  @Provides
  @Singleton
  internal fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread {
    return uiThread
  }

  @Provides
  @Singleton
  internal fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
    return jobExecutor
  }
}