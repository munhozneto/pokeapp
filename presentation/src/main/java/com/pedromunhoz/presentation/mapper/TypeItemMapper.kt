package com.pedromunhoz.presentation.mapper

import com.pedromunhoz.domain.model.TypeItem
import com.pedromunhoz.presentation.model.TypeItemBinding

class TypeItemMapper : Mapper<TypeItem, TypeItemBinding> {
    override fun toDomain(presentation: TypeItemBinding): TypeItem {
        return TypeItem(
            presentation.name
        )
    }

    override fun fromDomain(domain: TypeItem): TypeItemBinding {
        return TypeItemBinding(
            domain.name
        )
    }
}