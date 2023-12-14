package com.hans.chuckjokes.core.data.source


import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.hans.chuckjokes.core.data.source.local.LocalDataSource
import com.hans.chuckjokes.core.data.source.remote.RemoteDataSource
import com.hans.chuckjokes.core.data.source.remote.network.ApiResponse
import com.hans.chuckjokes.core.data.source.remote.response.JokesResponse
import com.hans.chuckjokes.core.domain.model.Jokes
import com.hans.chuckjokes.core.domain.repository.IJokesRepository
import com.hans.chuckjokes.core.utils.AppExecutors
import com.hans.chuckjokes.core.utils.DataMapper

class JokesRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IJokesRepository {

    companion object {
        @Volatile
        private var instance: JokesRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): JokesRepository =
            instance ?: synchronized(this) {
                instance ?: JokesRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getRandomJokes(): LiveData<Resource<List<Jokes>>> =
        object : NetworkBoundResource<List<Jokes>, List<JokesResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Jokes>> {
                return localDataSource.getRandomJokes().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Jokes>?): Boolean =
                true

            override fun createCall(): LiveData<ApiResponse<List<JokesResponse>>> =
                remoteDataSource.getRandomJokes()

            override fun saveCallResult(data: List<JokesResponse>) {
                val jokesList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertJokes(jokesList)
            }
        }.asLiveData()

    override fun getSearchJokes(query: String): LiveData<Resource<List<Jokes>>> =
        object : NetworkBoundResource<List<Jokes>, List<JokesResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Jokes>> {
                return localDataSource.getSearchJokes(query).map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Jokes>?): Boolean =
                true // Add your logic for deciding whether to fetch from network or not

            override fun createCall(): LiveData<ApiResponse<List<JokesResponse>>> =
                remoteDataSource.getSearchJokes(query)

            override fun saveCallResult(data: List<JokesResponse>) {
                val jokesList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertJokes(jokesList)
            }
        }.asLiveData()

    override fun getFavoriteJokes(): LiveData<List<Jokes>> {
        return localDataSource.getFavoriteJokes().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteJokes(jokes: Jokes, state: Boolean) {
        val jokesEntity = DataMapper.mapDomainToEntity(jokes)
        appExecutors.diskIO().execute { localDataSource.setFavoriteJokes(jokesEntity, state) }
    }
}