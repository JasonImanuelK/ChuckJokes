package com.hans.chuckjokes.core.domain.usecase

import androidx.lifecycle.LiveData
import com.hans.chuckjokes.core.data.source.Resource
import com.hans.chuckjokes.core.domain.model.Jokes

interface JokesUseCase {
    fun getRandomJokes(): LiveData<Resource<List<Jokes>>>
    fun getSearchJokes(query: String): LiveData<Resource<List<Jokes>>>
    fun getFavoriteJokes(): LiveData<List<Jokes>>
    fun setFavoriteJokes(jokes: Jokes, state: Boolean)
}