package com.pedromunhoz.data

import com.pedromunhoz.domain.model.Pokemon
import com.pedromunhoz.domain.model.PokemonClassic
import io.reactivex.Flowable

interface RemoteDataSource {
    fun getClassicPokemonList(): Flowable<MutableList<PokemonClassic>>

    fun catchPokemon(id: Int): Flowable<Pokemon?>
}