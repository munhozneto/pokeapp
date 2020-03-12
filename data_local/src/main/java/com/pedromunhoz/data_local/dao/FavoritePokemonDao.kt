package com.pedromunhoz.data_local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pedromunhoz.data_local.DataSourceContract
import com.pedromunhoz.data_local.entity.FavoritePokemonEntity
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface FavoritePokemonDao {
    @Query(DataSourceContract.HAS_FAVORITE_POKEMON)
    fun hasPokeFavorite(id: Int): Maybe<FavoritePokemonEntity>

    @Query(DataSourceContract.SELECT_FAVORITES_POKEMON_LIST)
    fun getPokeFavorites(): Maybe<MutableList<FavoritePokemonEntity>>

    @Insert
    fun insert(favoritePokemon: FavoritePokemonEntity): Completable

    @Query(DataSourceContract.DELETE_FAVORITE_POKEMON)
    fun delete(id: Int): Completable
}