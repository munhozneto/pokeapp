package com.pedromunhoz.domain.model

data class PokemonClassic(
    val id: Int,
    val pokemonSpecie: PokemonSpecie,
    val imageUrl: String,
    val isFavorite: Boolean
)