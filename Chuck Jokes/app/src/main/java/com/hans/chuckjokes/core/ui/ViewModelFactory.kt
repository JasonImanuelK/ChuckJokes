package com.hans.chuckjokes.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hans.chuckjokes.core.di.Injection
import com.hans.chuckjokes.core.domain.usecase.JokesUseCase
import com.hans.chuckjokes.favorite.FavoriteViewModel
import com.hans.chuckjokes.random.RandomViewModel
import com.hans.chuckjokes.search.ResultViewModel

class ViewModelFactory private constructor(private val jokesUseCase: JokesUseCase, private val query: String? = null
) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context, query: String? = null): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideJokesUseCase(context),
                    query
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(RandomViewModel::class.java) -> {
                RandomViewModel(jokesUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(jokesUseCase) as T
            }
            modelClass.isAssignableFrom(ResultViewModel::class.java) -> {
                ResultViewModel(jokesUseCase, query ?: "") as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}