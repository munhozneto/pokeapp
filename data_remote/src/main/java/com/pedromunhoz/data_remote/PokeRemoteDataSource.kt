package com.pedromunhoz.data_remote

import com.pedromunhoz.data.RemoteDataSource
import com.pedromunhoz.data_remote.mapper.PokemonClassicMapper
import com.pedromunhoz.data_remote.mapper.PokemonMapper
import com.pedromunhoz.data_remote.service.PokeApiService
import com.pedromunhoz.domain.model.Pokemon
import com.pedromunhoz.domain.model.PokemonClassic
import io.reactivex.Flowable
import io.reactivex.Single

class PokeRemoteDataSource(
    private val pokeApiService: PokeApiService
) : RemoteDataSource {

    override fun getClassicPokemonList(pokedexId: Int): Flowable<MutableList<PokemonClassic>> {
        return pokeApiService.listClassicPokemons(pokedexId)
            .map { responseList ->
                responseList.pokemonClassicList.map {
                    PokemonClassicMapper.parse(it)
                }.toMutableList()
            }
    }

    override fun catchPokemon(id: Int): Flowable<Pokemon?> {
        return pokeApiService.catchPokemon(id)
            .map {
                PokemonMapper.parse(it)
            }
    }
}