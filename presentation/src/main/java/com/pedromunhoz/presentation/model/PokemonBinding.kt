package com.pedromunhoz.presentation.model

data class PokemonBinding(
    val id: Int?,
    val name: String,
    val weight: Int,
    val height: Int,
    val abilities: MutableList<AbilityBinding>,
    val stats: MutableList<StatBinding>,
    val sprites: SpritesBinding,
    val baseExperience: Int,
    val types: MutableList<TypeBinding>
)


