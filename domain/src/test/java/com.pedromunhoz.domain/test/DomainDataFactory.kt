package com.pedromunhoz.domain.test

import com.pedromunhoz.data_local.test.DataFactory
import com.pedromunhoz.domain.model.*

object DomainDataFactory {
    fun makeFavoritePokemon() = FavoritePokemon(
        DataFactory.randomInt(),
        DataFactory.randomString()
    )

    fun makeFavoritePokemonList(count: Int): MutableList<FavoritePokemon> {
        val events = mutableListOf<FavoritePokemon>()
        repeat(count) {
            events.add(makeFavoritePokemon())
        }
        return events
    }

    fun makePokemonClassic() = PokemonClassic(
        DataFactory.randomInt(),
        makePokemonSpecie(),
        DataFactory.randomBoolean()
    )

    private fun makePokemonSpecie() = PokemonSpecie(
        DataFactory.randomString()
    )

    fun makeClassicPokemonList(count: Int): MutableList<PokemonClassic> {
        val events = mutableListOf<PokemonClassic>()
        repeat(count) {
            events.add(makePokemonClassic())
        }
        return events
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