package pl.ipebk.setsolver.presentation

import dagger.Module
import dagger.Provides
import pl.ipebk.setsolver.domain.DailySetEngine
import pl.ipebk.setsolver.domain.DailySetRemote
import pl.ipebk.setsolver.engine.DailySetEngineImpl
import pl.ipebk.setsolver.remote.DailySetRemoteImpl
import pl.ipebk.solver.SetGameSolver

@Module
class DomainModule {
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
}