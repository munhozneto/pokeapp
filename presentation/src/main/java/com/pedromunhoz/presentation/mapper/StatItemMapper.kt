package com.pedromunhoz.presentation.mapper

import com.pedromunhoz.domain.model.StatItem
import com.pedromunhoz.presentation.model.StatItemBinding

class StatItemMapper : Mapper<StatItem, StatItemBinding> {
    override fun toDomain(presentation: StatItemBinding): StatItem {
        return StatItem(
            presentation.name
        )
    }

    override fun fromDomain(domain: StatItem): StatItemBinding {
        return StatItemBinding(
            domain.name
        )
    }
}