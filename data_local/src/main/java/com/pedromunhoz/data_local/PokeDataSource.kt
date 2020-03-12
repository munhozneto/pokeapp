package com.pedromunhoz.data_local

import android.content.Context
import androidx.room.Room
import com.pedromunhoz.data.LocalDataSource
import com.pedromunhoz.data_local.DataSourceContract.POKE_DATABASE
import com.pedromunhoz.data_local.db.PokeDatabase
import com.pedromunhoz.data_local.mapper.FavoritePokemonMapper
import com.pedromunhoz.domain.model.FavoritePokemon
import io.reactivex.Completable
import io.reactivex.Maybe

class PokeDataSource(
    context: Context,
    allowMainThreadQueries: Boolean = false
) : LocalDataSource {

    private val database: PokeDatabase by lazy {
        val builder = Room.databaseBuilder(
            context.applicationContext, PokeDatabase::class.java, POKE_DATABASE
        )
        if (allowMainThreadQueries) {
            builder.allowMainThreadQueries()
        }
        builder.build()
    }

    private val favoritePokemonDao = database.favoritePokemonDao()

    override fun hasPokeFavorite(id: Int): Maybe<Boolean> {
        return favoritePokemonDao.hasPokeFavorite(id)
    }

    override fun getPokeFavorites(): Maybe<MutableList<FavoritePokemon>> {
        return favoritePokemonDao.getPokeFavorites()
            .map { list ->
                list.map {
                    FavoritePokemonMapper.toDomain(it)
                }.toMutableList()
            }
    }

    override fun insert(favoritePokemon: FavoritePokemon): Completable {
        return Completable.defer {
            favoritePokemonDao.insert(
                favoritePokemon = FavoritePokemonMapper.fromDomain(favoritePokemon)
            )
            Completable.complete()
        }
    }

    override fun delete(id: Int): Completable {
        return Completable.defer {
            favoritePokemonDao.delete(id)
            Completable.complete()
        }
    }

}