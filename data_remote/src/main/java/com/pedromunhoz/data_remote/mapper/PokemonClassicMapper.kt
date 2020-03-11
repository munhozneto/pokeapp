package com.pedromunhoz.data_remote.mapper

import com.pedromunhoz.data_remote.model.PokemonClassicResponse
import com.pedromunhoz.domain.model.PokemonClassic

object PokemonClassicMapper : Mapper<PokemonClassicResponse, PokemonClassic> {
    override fun parse(remote: PokemonClassicResponse): PokemonClassic {
        return PokemonClassic(
            remote.id,
            PokemonSpecieMapper.parse(remote.pokemonSpecie),
            "",
            false
        )
    }
}