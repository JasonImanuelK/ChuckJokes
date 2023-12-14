package com.hans.chuckjokes.core.di

import android.content.Context
import com.hans.chuckjokes.core.data.source.JokesRepository
import com.hans.chuckjokes.core.data.source.local.LocalDataSource
import com.hans.chuckjokes.core.data.source.local.room.JokesDatabase
import com.hans.chuckjokes.core.data.source.remote.RemoteDataSource
import com.hans.chuckjokes.core.data.source.remote.network.ApiConfig
import com.hans.chuckjokes.core.domain.repository.IJokesRepository
import com.hans.chuckjokes.core.domain.usecase.JokesInteractor
import com.hans.chuckjokes.core.domain.usecase.JokesUseCase
import com.hans.chuckjokes.core.utils.AppExecutors

object Injection {
    private fun provideRepository(context: Context): IJokesRepository {
        val database = JokesDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(database.jokesDao())
        val appExecutors = AppExecutors()

        return JokesRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideJokesUseCase(context: Context): JokesUseCase {
        val repository = provideRepository(context)
        return JokesInteractor(repository)
    }
}
