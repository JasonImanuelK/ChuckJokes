package com.hans.chuckjokes.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hans.chuckjokes.core.data.source.local.entity.JokesEntity

@Dao
interface JokesDao {
    @Query("SELECT * FROM jokes ORDER BY RANDOM() LIMIT 1;")
    fun getRandomJokes(): LiveData<List<JokesEntity>>

    @Query("SELECT * FROM jokes;")
    fun getSearchJokes(): LiveData<List<JokesEntity>>

    @Query("SELECT * FROM jokes where isFavorite = 1")
    fun getFavoriteJokes(): LiveData<List<JokesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertJokes(tourism: List<JokesEntity>)

    @Update
    fun updateFavoriteTourism(tourism: JokesEntity)
}