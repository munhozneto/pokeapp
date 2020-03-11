package com.pedromunhoz.data_remote.service

import com.pedromunhoz.data_remote.model.ClassicPokemonListResponse
import com.pedromunhoz.data_remote.model.PokemonResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {

    @GET(PokeApiSettings.CLASSIC_POKEMON_LIST)
    fun listClassicPokemons(
        @Path(PokeApiSettings.PARAM_POKEDEX_ID) pokedexId: Int
        = PokeApiSettings.PARAM_POKEDEX_ID_VALUE
    ): Single<ClassicPokemonListResponse>

    @GET(PokeApiSettings.CATCH)
    fun catchPokemon(@Path(PokeApiSettings.PARAM_POKEMON_ID) id: Int): Single<PokemonResponse>
}