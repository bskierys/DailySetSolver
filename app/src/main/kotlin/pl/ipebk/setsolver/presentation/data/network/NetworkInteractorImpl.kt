package pl.ipebk.setsolver.presentation.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.Completable
import io.reactivex.Observable
import pl.ipebk.setsolver.presentation.ApplicationQualifier
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkInteractorImpl @Inject constructor(
  private val connectivityManager: ConnectivityManager,
  @ApplicationQualifier private val context: Context) : NetworkInteractor {
  override fun hasNetworkConnection(): Boolean =
    connectivityManager.activeNetworkInfo?.isConnectedOrConnecting ?: false

  override fun hasNetworkConnectionCompletable(): Completable =
    if (hasNetworkConnection()) {
      Completable.complete()
    } else {
      Completable.error { NetworkInteractor.NetworkUnavailableException() }
    }

  override fun observeConnection(): Observable<Connectivity> {
    return ReactiveNetwork.observeNetworkConnectivity(context)
  }
}