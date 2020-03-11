package com.pedromunhoz.data_remote.mapper

import com.pedromunhoz.data_remote.model.StatItemResponse
import com.pedromunhoz.domain.model.StatItem

object StatItemMapper : Mapper<StatItemResponse, StatItem> {
    override fun parse(remote: StatItemResponse): StatItem {
        return StatItem(
            remote.name
        )
    }
}