package pl.ipebk.setsolver.presentation.appDi

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import pl.ipebk.setsolver.domain.DailySetEngine
import pl.ipebk.setsolver.domain.DailySetRemote
import pl.ipebk.setsolver.domain.interactor.FindDailySetSolution
import pl.ipebk.setsolver.engine.DailySetEngineImpl
import pl.ipebk.setsolver.remote.DailySetRemoteImpl
import pl.ipebk.solver.SetGameSolver
import javax.inject.Named

@Module
class DomainModule {
  @Provides
  fun provideFindDailySolutionCase(
    engine: DailySetEngine, remote: DailySetRemote,
    @Named(Rx.POOL) backgroundScheduler: Scheduler,
    @Named(Rx.MAIN) foregroundScheduler: Scheduler):
    FindDailySetSolution {

    return FindDailySetSolution(engine, remote, backgroundScheduler, foregroundScheduler)
  }

  @Provides
  fun provideSetGameSolver(): SetGameSolver {
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