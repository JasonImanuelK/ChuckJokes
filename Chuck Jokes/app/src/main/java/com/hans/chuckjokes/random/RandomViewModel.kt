package com.hans.chuckjokes.random

import androidx.lifecycle.ViewModel
import com.hans.chuckjokes.core.domain.model.Jokes
import com.hans.chuckjokes.core.domain.usecase.JokesUseCase

class RandomViewModel(private val jokesUseCase: JokesUseCase) : ViewModel() {
    val jokes = jokesUseCase.getRandomJokes()
    fun setFavoriteJokes(jokes: Jokes, newStatus:Boolean) = jokesUseCase.setFavoriteJokes(jokes, newStatus)
}