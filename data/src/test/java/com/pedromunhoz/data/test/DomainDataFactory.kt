package com.pedromunhoz.data.test

import com.pedromunhoz.data_local.test.DataFactory
import com.pedromunhoz.domain.model.FavoritePokemon
import com.pedromunhoz.domain.model.PokemonClassic
import com.pedromunhoz.domain.model.PokemonSpecie

object DomainDataFactory {
    fun makeFavoritePokemon() = FavoritePokemon(
        DataFactory.randomInt(),
        DataFactory.randomString()
    )

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
}