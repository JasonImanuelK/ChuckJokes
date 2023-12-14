package com.hans.chuckjokes.core.utils

import com.hans.chuckjokes.core.data.source.local.entity.JokesEntity
import com.hans.chuckjokes.core.data.source.remote.response.JokesResponse
import com.hans.chuckjokes.core.domain.model.Jokes

object DataMapper {
    fun mapResponsesToEntities(input: List<JokesResponse>): List<JokesEntity> {
        val jokesList = ArrayList<JokesEntity>()
        input.map {
            val jokes = JokesEntity(
                icon_url = it.icon_url,
                id = it.id,
                url = it.url,
                value = it.value,
                isFavorite = false
            )
            jokesList.add(jokes)
        }
        return jokesList
    }

    fun mapEntitiesToDomain(input: List<JokesEntity>): List<Jokes> =
        input.map {
            Jokes(
                icon_url = it.icon_url,
                id = it.id,
                url = it.url,
                value = it.value,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Jokes) = JokesEntity(
        icon_url = input.icon_url,
        id = input.id,
        url = input.url,
        value = input.value,
        isFavorite = input.isFavorite
    )
}