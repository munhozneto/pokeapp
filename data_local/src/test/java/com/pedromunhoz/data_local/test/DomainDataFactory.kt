package com.pedromunhoz.data_local.test

import com.pedromunhoz.domain.model.FavoritePokemon

object DomainDataFactory {
    fun makeFavoritePokemon() = FavoritePokemon(
        DataFactory.randomInt(),
        DataFactory.randomString(),
        DataFactory.randomString()
    )
}