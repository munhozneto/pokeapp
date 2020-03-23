package com.pedromunhoz.data_local.test

import com.pedromunhoz.data_local.entity.FavoritePokemonEntity

object EntityDataFactory {
    fun makeFavoritePokemon() = FavoritePokemonEntity(
        DataFactory.randomInt(),
        DataFactory.randomString()
    )

    fun makeFavoritePokemonList(count: Int): MutableList<FavoritePokemonEntity> {
        val list = mutableListOf<FavoritePokemonEntity>()
        repeat(count) {
            list.add(makeFavoritePokemon())
        }
        return list
    }
}