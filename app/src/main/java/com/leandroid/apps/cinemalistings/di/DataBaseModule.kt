package com.leandroid.apps.cinemalistings.di

import android.content.Context
import androidx.room.Room
import com.leandroid.apps.cinemalistings.model.MovieDao
import com.leandroid.apps.cinemalistings.model.MovieDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Singleton
    @Provides
    fun provideMovieDataBase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        MovieDataBase::class.java,
        "movieDB"
    ).build()

    @Singleton
    @Provides
    fun provideMovieDao(db: MovieDataBase) = db.movieDao()
}