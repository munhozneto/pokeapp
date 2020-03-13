package com.pedromunhoz.presentation.test

import com.pedromunhoz.domain.model.FavoritePokemon
import com.pedromunhoz.domain.model.PokemonClassic
import com.pedromunhoz.domain.model.PokemonSpecie

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

    fun makePokemonSpecie() = PokemonSpecie(
        DataFactory.randomString()
    )

    fun makeClassicPokemonList(count: Int): MutableList<PokemonClassic> {
        val events = mutableListOf<PokemonClassic>()
        repeat(count) {
            events.add(makePokemonClassic())
        }
        return events
    }
}