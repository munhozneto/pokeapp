package com.pedromunhoz.data_remote.mapper

import com.pedromunhoz.data_remote.model.TypeResponse
import com.pedromunhoz.domain.model.Type

object TypeMapper : Mapper<TypeResponse, Type> {
    override fun parse(remote: TypeResponse): Type {
        return Type(
            TypeItemMapper.parse(remote.type)
        )
    }
}