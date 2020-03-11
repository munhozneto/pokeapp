package com.pedromunhoz.data

import com.pedromunhoz.domain.model.ClassicPokemonList
import com.pedromunhoz.domain.model.Pokemon
import com.pedromunhoz.domain.model.PokemonClassic
import io.reactivex.Single
import javax.xml.ws.Response

interface RemoteDataSource {
    fun listClassicPokemons(pokedexId: Int): Single<MutableList<PokemonClassic>>

    fun catchPokemon(id: Int): Single<Pokemon?>
}