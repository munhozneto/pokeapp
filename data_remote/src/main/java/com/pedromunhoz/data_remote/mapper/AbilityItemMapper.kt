package com.pedromunhoz.data_remote.mapper

import com.pedromunhoz.data_remote.model.AbilityItemResponse
import com.pedromunhoz.domain.model.AbilityItem

object AbilityItemMapper : Mapper<AbilityItemResponse, AbilityItem> {
    override fun parse(remote: AbilityItemResponse): AbilityItem {
        return AbilityItem(
            remote.name
        )
    }
}