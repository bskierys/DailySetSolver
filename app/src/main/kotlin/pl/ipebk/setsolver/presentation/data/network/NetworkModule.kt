package pl.ipebk.setsolver.presentation.data.network

import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import pl.ipebk.setsolver.presentation.appDi.ApplicationScope
import javax.inject.Singleton

@Module
class NetworkModule {

  @Provides
  @Singleton
  fun provideConnectivityManager(@ApplicationScope context: Context):
    ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
    as ConnectivityManager

  @Provides
  @Singleton
  fun provideNetworkInteractor(networkInteractorImpl: NetworkInteractorImpl):
    NetworkInteractor = networkInteractorImpl

}