package com.hans.chuckjokes.core.domain.usecase

import com.hans.chuckjokes.core.domain.model.Jokes
import com.hans.chuckjokes.core.domain.repository.IJokesRepository

class JokesInteractor (private val jokesRepository: IJokesRepository): JokesUseCase {

    override fun getRandomJokes() = jokesRepository.getRandomJokes()

    override fun getSearchJokes(query: String) = jokesRepository.getSearchJokes(query)

    override fun getFavoriteJokes() = jokesRepository.getFavoriteJokes()

    override fun setFavoriteJokes(jokes: Jokes, state: Boolean) = jokesRepository.setFavoriteJokes(jokes, state)
}