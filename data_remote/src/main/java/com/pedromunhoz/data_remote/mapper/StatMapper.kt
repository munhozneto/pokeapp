package com.pedromunhoz.data_remote.mapper

import com.pedromunhoz.data_remote.model.StatResponse
import com.pedromunhoz.domain.model.Stat

object StatMapper : Mapper<StatResponse, Stat> {
    override fun parse(remote: StatResponse): Stat {
        return Stat(
            StatItemMapper.parse(remote.stat),
            remote.baseStat
        )
    }
}