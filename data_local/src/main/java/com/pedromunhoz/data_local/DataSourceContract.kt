package com.pedromunhoz.data_local

object DataSourceContract {
    const val POKE_DATABASE = "pokedatasource.db"
    const val HAS_FAVORITE_POKEMON = "SELECT COUNT(*) FROM FAVORITEPOKEMONENTITY WHERE id = :id"
    const val SELECT_FAVORITES_POKEMON_LIST = "SELECT * FROM FAVORITEPOKEMONENTITY"
    const val DELETE_FAVORITE_POKEMON = "DELETE FROM FAVORITEPOKEMONENTITY where id = :id"
}