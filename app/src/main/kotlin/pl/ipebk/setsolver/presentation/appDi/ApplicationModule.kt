package pl.ipebk.setsolver.presentation.appDi

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import com.github.bskierys.pine.Pine
import dagger.Module
import dagger.Provides
import net.ypresto.timbertreeutils.CrashlyticsLogExceptionTree
import pl.ipebk.setsolver.BuildConfig
import pl.ipebk.setsolver.presentation.DailySetSolverApp
import timber.log.Timber
import javax.inject.Singleton

@Module
class ApplicationModule(private val app: DailySetSolverApp) {

  @Provides
  @Singleton
  fun provideApplication(): Application = app

  @Provides
  @Singleton
  @ApplicationScope
  fun provideContext(): Context = app.baseContext

  @Provides
  @Singleton
  fun provideLayoutInflater(@ApplicationScope context: Context): LayoutInflater {
    return LayoutInflater.from(context)
  }

  @Provides
  fun provideTimberTree(): Timber.Tree {
    return when (BuildConfig.DEBUG) {
      true -> Pine.Builder()
        .addPackageReplacePattern(app.packageName, "SETAPP")
        .grow()
      false -> CrashlyticsLogExceptionTree()
    }
  }
}