package com.pedromunhoz.presentation.mapper

import com.pedromunhoz.domain.model.PokemonSpecie
import com.pedromunhoz.presentation.model.PokemonSpecieBinding

class PokemonSpecieMapper : Mapper<PokemonSpecie, PokemonSpecieBinding> {
    override fun toDomain(presentation: PokemonSpecieBinding): PokemonSpecie {
        return PokemonSpecie(
            presentation.name
        )
    }

    override fun fromDomain(domain: PokemonSpecie): PokemonSpecieBinding {
        return PokemonSpecieBinding(
            domain.name
        )
    }
}