package com.pedromunhoz.presentation.mapper

import com.pedromunhoz.domain.model.AbilityItem
import com.pedromunhoz.presentation.model.AbilityItemBinding

class AbilityItemMapper : Mapper<AbilityItem, AbilityItemBinding> {
    override fun toDomain(presentation: AbilityItemBinding): AbilityItem {
        return AbilityItem(
            presentation.name
        )
    }

    override fun fromDomain(domain: AbilityItem): AbilityItemBinding {
        return AbilityItemBinding(
            domain.name
        )
    }
}