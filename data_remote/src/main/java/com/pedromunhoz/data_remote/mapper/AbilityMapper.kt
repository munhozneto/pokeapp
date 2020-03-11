package com.pedromunhoz.data_remote.mapper

import com.pedromunhoz.data_remote.model.AbilityResponse
import com.pedromunhoz.domain.model.Ability

object AbilityMapper : Mapper<AbilityResponse, Ability> {
    override fun parse(remote: AbilityResponse): Ability {
        return Ability(
            AbilityItemMapper.parse(remote.ability)
        )
    }
}