package com.pedromunhoz.presentation.mapper

import com.pedromunhoz.domain.model.Type
import com.pedromunhoz.presentation.model.TypeBinding

class TypeMapper(
    private val typeItemMapper: TypeItemMapper
) : Mapper<Type, TypeBinding> {
    override fun toDomain(presentation: TypeBinding): Type {
        return Type(
            typeItemMapper.toDomain(presentation.type)
        )
    }

    override fun fromDomain(domain: Type): TypeBinding {
        return TypeBinding(
            typeItemMapper.fromDomain(domain.type)
        )
    }
}