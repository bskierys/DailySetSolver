package pl.ipebk.setsolver.ui.detail

import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import pl.ipebk.setsolver.data.remote.model.Repo
import pl.ipebk.setsolver.ui.base.ActivityModule

@Module
class DetailModule(activity: AppCompatActivity, val repo: Repo) : ActivityModule(activity) {

    @Provides
    fun provideRepo(): Repo = repo
}