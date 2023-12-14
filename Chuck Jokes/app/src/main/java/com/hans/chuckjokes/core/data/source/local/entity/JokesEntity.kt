package com.hans.chuckjokes.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.Arrays

@Entity(tableName = "jokes")
data class JokesEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "icon_url")
    var icon_url: String,

    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "url")
    var url: String,

    @ColumnInfo(name = "value")
    var value: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)