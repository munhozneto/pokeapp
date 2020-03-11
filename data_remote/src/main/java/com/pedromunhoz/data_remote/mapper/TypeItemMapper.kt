package com.pedromunhoz.data_remote.mapper

import com.pedromunhoz.data_remote.model.TypeItemResponse
import com.pedromunhoz.domain.model.TypeItem

object TypeItemMapper : Mapper<TypeItemResponse, TypeItem> {
    override fun parse(remote: TypeItemResponse): TypeItem {
        return TypeItem(
            remote.name
        )
    }
}