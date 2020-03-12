package com.pedromunhoz.presentation.mapper

import com.pedromunhoz.domain.model.Pokemon
import com.pedromunhoz.presentation.model.PokemonBinding

class PokemonMapper(
    private val abilityMapper: AbilityMapper,
    private val statMapper: StatMapper,
    private val spritesMapper: SpritesMapper,
    private val typeMapper: TypeMapper
) : Mapper<Pokemon, PokemonBinding> {
    override fun toDomain(presentation: PokemonBinding): Pokemon {
        return Pokemon(
            presentation.id,
            presentation.name,
            presentation.weight,
            presentation.height,
            presentation.abilities.map { abilityMapper.toDomain(it) }.toMutableList(),
            presentation.stats.map { statMapper.toDomain(it) }.toMutableList(),
            spritesMapper.toDomain(presentation.sprites),
            presentation.baseExperience,
            presentation.types.map { typeMapper.toDomain(it) }.toMutableList()
        )
    }

    override fun fromDomain(domain: Pokemon): PokemonBinding {
        return PokemonBinding(
            domain.id,
            domain.name,
            domain.weight,
            domain.height,
            domain.abilities.map { abilityMapper.fromDomain(it) }.toMutableList(),
            domain.stats.map { statMapper.fromDomain(it) }.toMutableList(),
            spritesMapper.fromDomain(domain.sprites),
            domain.baseExperience,
            domain.types.map { typeMapper.fromDomain(it) }.toMutableList()
        )
    }
}