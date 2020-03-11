package com.pedromunhoz.data_remote.mapper

import com.pedromunhoz.data_remote.model.PokemonResponse
import com.pedromunhoz.domain.model.Pokemon

object PokemonMapper : Mapper<PokemonResponse, Pokemon> {
    override fun parse(remote: PokemonResponse): Pokemon {
        return Pokemon(
            remote.id,
            remote.name,
            remote.weight,
            remote.height,
            remote.abilities.map { AbilityMapper.parse(it) }.toMutableList(),
            remote.stats.map { StatMapper.parse(it) }.toMutableList(),
            SpritesMapper.parse(remote.sprites),
            remote.baseExperience,
            remote.types.map { TypeMapper.parse(it) }.toMutableList()
        )
    }
}