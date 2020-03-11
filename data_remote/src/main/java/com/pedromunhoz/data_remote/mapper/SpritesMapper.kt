package com.pedromunhoz.data_remote.mapper

import com.pedromunhoz.data_remote.model.SpritesResponse
import com.pedromunhoz.domain.model.Sprites

object SpritesMapper : Mapper<SpritesResponse, Sprites> {
    override fun parse(remote: SpritesResponse): Sprites {
        return Sprites(
            remote.backDefault,
            remote.frontDefault
        )
    }
}