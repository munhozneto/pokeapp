package com.pedromunhoz.data_remote.model

import com.google.gson.annotations.SerializedName


data class PokemonClassicResponse(
    @SerializedName("entry_number") val id: Int,
    @SerializedName("pokemon_species") val pokemonSpecie: PokemonSpecieResponse
)