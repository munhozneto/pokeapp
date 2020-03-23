package com.pedromunhoz.data_remote.test

import com.pedromunhoz.data_remote.model.ClassicPokemonListResponse
import com.pedromunhoz.data_remote.model.PokemonClassicResponse
import com.pedromunhoz.data_remote.model.PokemonSpecieResponse
import com.pedromunhoz.domain.model.PokemonClassic
import com.pedromunhoz.domain.model.PokemonSpecie

object RemoteDataFactory {
    private fun makePokemonClassic() = PokemonClassicResponse(
        DataFactory.randomInt(),
        makePokemonSpecie()
    )

    private fun makePokemonSpecie() = PokemonSpecieResponse(
        DataFactory.randomString()
    )

    fun makeClassicPokemonList(count: Int): ClassicPokemonListResponse {
        val list = mutableListOf<PokemonClassicResponse>()
        repeat(count) {
            list.add(makePokemonClassic())
        }
        return ClassicPokemonListResponse(list)
    }
}