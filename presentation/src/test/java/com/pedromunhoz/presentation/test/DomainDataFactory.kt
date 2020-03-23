package com.pedromunhoz.presentation.test

import com.pedromunhoz.domain.model.*

object DomainDataFactory {
    fun makeFavoritePokemon() = FavoritePokemon(
        DataFactory.randomInt(),
        DataFactory.randomString()
    )

    fun makeFavoritePokemonList(count: Int): MutableList<FavoritePokemon> {
        val list = mutableListOf<FavoritePokemon>()
        repeat(count) {
            list.add(makeFavoritePokemon())
        }
        return list
    }

    fun makePokemonClassic() = PokemonClassic(
        DataFactory.randomInt(),
        makePokemonSpecie(),
        DataFactory.randomBoolean()
    )

    fun makePokemonSpecie() = PokemonSpecie(
        DataFactory.randomString()
    )

    fun makeClassicPokemonList(count: Int): MutableList<PokemonClassic> {
        val list = mutableListOf<PokemonClassic>()
        repeat(count) {
            list.add(makePokemonClassic())
        }
        return list
    }

    fun makePokemon() = Pokemon(
        DataFactory.randomInt(),
        DataFactory.randomString(),
        DataFactory.randomInt(),
        DataFactory.randomInt(),
        mutableListOf(),
        mutableListOf(),
        makeSprites(),
        DataFactory.randomInt(),
        mutableListOf()
    )

    private fun makeSprites() = Sprites(
        DataFactory.randomString(),
        DataFactory.randomString()
    )
}