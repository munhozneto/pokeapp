package com.pedromunhoz.domain.model

data class Pokemon(
    val id: Int?,
    val name: String,
    val weight: Int,
    val height: Int,
    val abilities: MutableList<Ability>,
    val stats: MutableList<Stat>,
    val sprites: Sprites,
    val baseExperience: Int,
    val types: MutableList<Type>
)


