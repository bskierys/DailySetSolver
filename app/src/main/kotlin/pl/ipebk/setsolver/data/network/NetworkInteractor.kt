package pl.ipebk.setsolver.data.network

import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import io.reactivex.Completable
import io.reactivex.Observable

interface NetworkInteractor {

  fun hasNetworkConnection(): Boolean

  fun hasNetworkConnectionCompletable(): Completable

  fun observeConnection(): Observable<Connectivity>

  class NetworkUnavailableException : Throwable("No network available!")
}