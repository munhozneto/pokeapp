package com.pedromunhoz.data_local.mapper

import com.pedromunhoz.data_local.entity.FavoritePokemonEntity
import com.pedromunhoz.domain.model.FavoritePokemon

object FavoritePokemonMapper : Mapper<FavoritePokemonEntity, FavoritePokemon> {
    override fun toDomain(entity: FavoritePokemonEntity): FavoritePokemon {
        return FavoritePokemon(
            entity.id,
            entity.name,
            entity.imgUrl
        )
    }

    override fun fromDomain(domain: FavoritePokemon): FavoritePokemonEntity {
        return FavoritePokemonEntity(
            domain.id,
            domain.name,
            domain.imgUrl
        )
    }
}