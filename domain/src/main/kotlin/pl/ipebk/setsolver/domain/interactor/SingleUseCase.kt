package pl.ipebk.setsolver.domain.interactor

import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

/**
 * Abstract class for a UseCase that returns an instance of a [Single].
 */
abstract class SingleUseCase<T, in Params> {

  private val disposables = CompositeDisposable()

  /**
   * Builds a [Single] which will be used when the current [SingleUseCase] is executed.
   */
  protected abstract fun buildUseCaseObservable(params: Params? = null): Single<T>

  /**
   * Executes the current use case.
   */
  fun execute(params: Params? = null) :Single<T> {
    return buildUseCaseObservable(params)
  }
}