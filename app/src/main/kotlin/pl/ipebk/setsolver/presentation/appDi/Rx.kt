package pl.ipebk.setsolver.presentation.appDi

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class Rx {
  companion object {
    const val MAIN = "main"
    const val IO = "io"
    const val COMPUTATION = "computation"
    const val POOL = "pool"
  }

  @Provides
  @Singleton
  @Named(MAIN)
  fun provideMainScheduler(): Scheduler {
    return AndroidSchedulers.mainThread()
  }

  @Provides
  @Singleton
  @Named(IO)
  fun provideIoScheduler(): Scheduler {
    return Schedulers.io()
  }

  @Provides
  @Singleton
  @Named(COMPUTATION)
  fun provideComputationScheduler(): Scheduler {
    return Schedulers.computation()
  }

  @Provides
  @Named(POOL)
  fun provideBackgroundPoolScheduler(): Scheduler {
    return Schedulers.newThread()
  }
}