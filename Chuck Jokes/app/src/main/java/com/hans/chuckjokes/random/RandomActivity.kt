package com.hans.chuckjokes.random

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.hans.chuckjokes.R
import com.hans.chuckjokes.core.data.source.Resource
import com.hans.chuckjokes.core.domain.model.Jokes
import com.hans.chuckjokes.core.ui.ViewModelFactory
import com.hans.chuckjokes.databinding.ActivityRandomBinding

class RandomActivity : AppCompatActivity() {
    private lateinit var randomViewModel: RandomViewModel
    private lateinit var binding: ActivityRandomBinding
    private var detailJokes = Jokes(
        icon_url = "https://example.com/icon.png",
        id = "123",
        url = "https://example.com/joke",
        value = "Chuck Norris joke",
        isFavorite = false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.v("RandomActivity", "onCreate: ViewBinding and layout set successfully")

        val factory = ViewModelFactory.getInstance(this)
        randomViewModel = ViewModelProvider(this, factory)[RandomViewModel::class.java]

        randomViewModel.jokes.observe(this, { jokes ->
            when (jokes) {
                is Resource.Loading -> {
                    Log.v("RandomActivity", "onCreate: Loading state")
                    // Handle loading state if needed
                }
                is Resource.Success -> {
                    val data = jokes.data
                    Log.v("RandomActivity", "onCreate: Data received - $data")
                    if (!data.isNullOrEmpty()) {
                        val firstJokeItem = data[0]
                        detailJokes.icon_url = firstJokeItem.icon_url
                        detailJokes.id = firstJokeItem.id
                        detailJokes.url = firstJokeItem.url
                        detailJokes.value = firstJokeItem.value
                        detailJokes.isFavorite = firstJokeItem.isFavorite
                        displayJokeItem(firstJokeItem)
                    }
                }
                is Resource.Error -> {
                    Log.e("RandomActivity", "onCreate: Error - ${jokes.message}")
                    // Handle error state if needed
                }
                else -> {

                }
            }
        })

        var statusFavorite = detailJokes.isFavorite
        setStatusFavorite(statusFavorite)
        binding.fab.setOnClickListener {
            statusFavorite = !statusFavorite
            randomViewModel.setFavoriteJokes(detailJokes, statusFavorite)
            setStatusFavorite(statusFavorite)
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_red))
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_not_red))
        }
    }

    private fun displayJokeItem(jokes: Jokes) {
        with(binding) {
            // Assuming there is a single TextView in your layout
            textViewJokes.text = jokes.value
        }
    }
}