package pl.ipebk.setsolver.presentation.ui.solver

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import pl.ipebk.setsolver.domain.DailySetEngine
import pl.ipebk.setsolver.domain.DailySetRemote
import pl.ipebk.setsolver.engine.CardMapper
import pl.ipebk.setsolver.engine.CardMapperImpl
import pl.ipebk.setsolver.engine.DailySetEngineImpl
import pl.ipebk.setsolver.presentation.ui.base.ActivityModule
import pl.ipebk.setsolver.remote.DailySetRemoteImpl
import pl.ipebk.solver.SetGameSolver

@Module
class SolverModule(activity: AppCompatActivity) : ActivityModule(activity) {
  @Provides
  internal fun provideSetGameSolver(): SetGameSolver {
    return SetGameSolver(4, 3)
  }

  @Provides
  fun provideDailySetEngine(impl: DailySetEngineImpl): DailySetEngine {
    return impl
  }

  @Provides
  fun provideDailySetRemote(impl: DailySetRemoteImpl): DailySetRemote {
    return impl
  }

  @Provides
  fun provideCardMapper(impl: CardMapperImpl): CardMapper {
    return impl
  }

  @Provides
  fun provideLinearLayoutManager(context: Context): LinearLayoutManager =
    LinearLayoutManager(context)
}