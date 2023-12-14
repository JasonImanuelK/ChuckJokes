package com.hans.chuckjokes.favorite

import androidx.lifecycle.ViewModel
import com.hans.chuckjokes.core.domain.model.Jokes
import com.hans.chuckjokes.core.domain.usecase.JokesUseCase

class FavoriteViewModel(private val jokesUseCase: JokesUseCase) : ViewModel() {
    val favoriteJokes= jokesUseCase.getFavoriteJokes()
    fun setFavoriteJokes(jokes: Jokes, newStatus:Boolean) = jokesUseCase.setFavoriteJokes(jokes, newStatus)
}