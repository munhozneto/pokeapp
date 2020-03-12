package com.pedromunhoz.presentation.mapper

import com.pedromunhoz.domain.model.Sprites
import com.pedromunhoz.presentation.model.SpritesBinding

class SpritesMapper : Mapper<Sprites, SpritesBinding> {
    override fun toDomain(presentation: SpritesBinding): Sprites {
        return Sprites(
            presentation.backDefault,
            presentation.frontDefault
        )
    }

    override fun fromDomain(domain: Sprites): SpritesBinding {
        return SpritesBinding(
            domain.backDefault,
            domain.frontDefault
        )
    }
}