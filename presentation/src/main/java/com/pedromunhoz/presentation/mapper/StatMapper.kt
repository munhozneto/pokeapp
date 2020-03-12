package com.pedromunhoz.presentation.mapper

import com.pedromunhoz.domain.model.Stat
import com.pedromunhoz.presentation.model.StatBinding

class StatMapper(
    private val statItemMapper: StatItemMapper
) : Mapper<Stat, StatBinding> {
    override fun toDomain(presentation: StatBinding): Stat {
        return Stat(
            statItemMapper.toDomain(presentation.stat),
            presentation.baseStat
        )
    }

    override fun fromDomain(domain: Stat): StatBinding {
        return StatBinding(
            statItemMapper.fromDomain(domain.stat),
            domain.baseStat
        )
    }
}