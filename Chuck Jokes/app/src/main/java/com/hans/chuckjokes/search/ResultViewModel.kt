package com.hans.chuckjokes.search

import androidx.lifecycle.ViewModel
import com.hans.chuckjokes.core.domain.usecase.JokesUseCase

class ResultViewModel(private val jokesUseCase: JokesUseCase, private val query: String) : ViewModel() {
    val jokes = jokesUseCase.getSearchJokes(query)
}