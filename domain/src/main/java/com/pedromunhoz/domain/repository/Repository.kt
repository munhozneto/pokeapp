package com.pedromunhoz.domain.repository

import com.pedromunhoz.domain.model.FavoritePokemon
import com.pedromunhoz.domain.model.Pokemon
import com.pedromunhoz.domain.model.PokemonClassic
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface Repository {
    fun getClassicPokemonList(): Flowable<MutableList<PokemonClassic>>

    fun catchPokemon(id: Int): Flowable<Pokemon?>

    fun updateFavorite(favoritePokemon: FavoritePokemon): Maybe<Int>

    fun getPokeFavorites(): Maybe<MutableList<FavoritePokemon>>
}