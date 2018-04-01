package pl.ipebk.setsolver.domain.interactor

import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables

/**
 * Abstract class for a UseCase that returns an instance of a [Completable].
 */
abstract class CompletableUseCase<in Params> protected constructor(
  private val backgroundScheduler: Scheduler,
  private val foregroundScheduler: Scheduler) {

  private val subscription = Disposables.empty()

  /**
   * Builds a [Completable] which will be used when the current [CompletableUseCase] is executed.
   */
  protected abstract fun buildUseCaseObservable(params: Params): Completable

  /**
   * Executes the current use case.
   */
  fun execute(params: Params): Completable {
    return this.buildUseCaseObservable(params)
      .subscribeOn(backgroundScheduler)
      .observeOn(foregroundScheduler)
  }

  /**
   * Unsubscribes from current [Disposable].
   */
  fun unsubscribe() {
    if (!subscription.isDisposed) {
      subscription.dispose()
    }
  }
}