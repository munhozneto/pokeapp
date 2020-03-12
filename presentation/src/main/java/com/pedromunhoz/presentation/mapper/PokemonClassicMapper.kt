package com.pedromunhoz.presentation.mapper

import com.pedromunhoz.domain.model.PokemonClassic
import com.pedromunhoz.presentation.model.PokemonClassicBinding

class PokemonClassicMapper(
    private val pokemonSpecieMapper: PokemonSpecieMapper
) : Mapper<PokemonClassic, PokemonClassicBinding> {
    override fun toDomain(presentation: PokemonClassicBinding): PokemonClassic {
        return PokemonClassic(
            presentation.id,
            pokemonSpecieMapper.toDomain(presentation.pokemonSpecie),
            presentation.isFavorite
        )
    }

    override fun fromDomain(domain: PokemonClassic): PokemonClassicBinding {
        return PokemonClassicBinding(
            domain.id,
            pokemonSpecieMapper.fromDomain(domain.pokemonSpecie),
            domain.isFavorite
        )
    }
}