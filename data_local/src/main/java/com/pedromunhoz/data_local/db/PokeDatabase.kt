package com.pedromunhoz.data_local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pedromunhoz.data_local.dao.FavoritePokemonDao
import com.pedromunhoz.data_local.entity.FavoritePokemonEntity

@Database(
    entities = [
        FavoritePokemonEntity::class
    ],
    version = 1
)
abstract class PokeDatabase : RoomDatabase() {
    abstract fun favoritePokemonDao(): FavoritePokemonDao
}