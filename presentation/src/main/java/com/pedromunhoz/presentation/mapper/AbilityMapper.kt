package com.pedromunhoz.presentation.mapper

import com.pedromunhoz.domain.model.Ability
import com.pedromunhoz.presentation.model.AbilityBinding

class AbilityMapper(
    private val abilityItemMapper: AbilityItemMapper
) : Mapper<Ability, AbilityBinding> {
    override fun toDomain(presentation: AbilityBinding): Ability {
        return Ability(
            abilityItemMapper.toDomain(presentation.ability)
        )
    }

    override fun fromDomain(domain: Ability): AbilityBinding {
        return AbilityBinding(
            abilityItemMapper.fromDomain(domain.ability)
        )
    }
}