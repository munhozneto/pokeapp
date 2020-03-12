package com.pedromunhoz.presentation.mapper

import com.pedromunhoz.domain.model.FavoritePokemon
import com.pedromunhoz.presentation.model.FavoritePokemonBinding

class FavoritePokemonMapper : Mapper<FavoritePokemon, FavoritePokemonBinding> {
    override fun toDomain(presentation: FavoritePokemonBinding): FavoritePokemon {
        return FavoritePokemon(
            presentation.id,
            presentation.name,
            presentation.imgUrl
        )
    }

    override fun fromDomain(domain: FavoritePokemon): FavoritePokemonBinding {
        return FavoritePokemonBinding(
            domain.id,
            domain.name,
            domain.imgUrl
        )
    }
}