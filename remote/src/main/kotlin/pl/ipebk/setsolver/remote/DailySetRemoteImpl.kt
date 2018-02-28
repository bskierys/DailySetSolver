package pl.ipebk.setsolver.remote

import io.reactivex.Single
import pl.ipebk.setsolver.domain.DailySetRemote
import pl.ipebk.setsolver.domain.SetCard
import pl.ipebk.setsolver.remote.mapper.DailySetEntityMapper

/**
 * Remote implementation for retrieving Cards for daily puzzle. This class implements the
 * [DailySetRemote] from the Domain layer
 */
class DailySetRemoteImpl(private val apiService: DailySetApiService,
  // we are injecting here implementation? why interface then?
                         private val entityMapper: DailySetEntityMapper) : DailySetRemote {
  override fun getPuzzleCards(): Single<List<SetCard>> {
    return apiService.getTodayPuzzle()
      .map {
        it.map { entityMapper.mapFromRemote(it) }
      }
  }
}