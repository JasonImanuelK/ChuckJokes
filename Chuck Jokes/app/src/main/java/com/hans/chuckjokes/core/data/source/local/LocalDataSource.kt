package com.hans.chuckjokes.core.data.source.local

import androidx.lifecycle.LiveData
import com.hans.chuckjokes.core.data.source.local.entity.JokesEntity
import com.hans.chuckjokes.core.data.source.local.room.JokesDao

class LocalDataSource private constructor(private val jokesDao: JokesDao) {

    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(jokesDao: JokesDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(jokesDao)
            }
    }

    fun getRandomJokes(): LiveData<List<JokesEntity>> = jokesDao.getRandomJokes()

    fun getFavoriteJokes(): LiveData<List<JokesEntity>> = jokesDao.getFavoriteJokes()

    fun insertJokes(jokesList: List<JokesEntity>) = jokesDao.insertJokes(jokesList)

    fun getSearchJokes(query: String): LiveData<List<JokesEntity>> = jokesDao.getSearchJokes()

    fun setFavoriteJokes(jokes: JokesEntity, newState: Boolean) {
        jokes.isFavorite = newState
        jokesDao.updateFavoriteTourism(jokes)
    }
}