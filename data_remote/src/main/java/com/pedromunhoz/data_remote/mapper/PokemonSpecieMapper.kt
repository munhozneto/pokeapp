package com.pedromunhoz.data_remote.mapper

import com.pedromunhoz.data_remote.model.PokemonSpecieResponse
import com.pedromunhoz.domain.model.PokemonSpecie

object PokemonSpecieMapper : Mapper<PokemonSpecieResponse, PokemonSpecie> {
    override fun parse(remote: PokemonSpecieResponse): PokemonSpecie {
        return PokemonSpecie(
            remote.name
        )
    }
}