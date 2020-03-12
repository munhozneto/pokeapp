package com.pedromunhoz.domain.repository

import com.pedromunhoz.domain.model.FavoritePokemon
import com.pedromunhoz.domain.model.Pokemon
import com.pedromunhoz.domain.model.PokemonClassic
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface Repository {
    fun getClassicPokemonList(pokedexId: Int): Single<MutableList<PokemonClassic>>

    fun catchPokemon(id: Int): Single<Pokemon?>

    fun updateFavorite(favoritePokemon: FavoritePokemon): Completable

    fun getPokeFavorites(): Maybe<MutableList<FavoritePokemon>>
}