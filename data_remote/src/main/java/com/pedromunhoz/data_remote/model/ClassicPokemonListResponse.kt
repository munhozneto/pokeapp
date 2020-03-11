package com.pedromunhoz.data_remote.model

import com.google.gson.annotations.SerializedName


data class ClassicPokemonListResponse(
        @SerializedName("pokemon_entries") val pokemonClassicList: MutableList<PokemonClassicResponse>
)