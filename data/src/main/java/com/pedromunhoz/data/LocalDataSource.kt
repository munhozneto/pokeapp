package com.pedromunhoz.data

import com.pedromunhoz.domain.model.FavoritePokemon
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface LocalDataSource {

    fun hasPokeFavorite(id: Int): Maybe<Boolean>

    fun getPokeFavorites():  Maybe<MutableList<FavoritePokemon>>

    fun insert(favoritePokemon: FavoritePokemon):  Maybe<Int>

    fun delete(id: Int):  Maybe<Int>
}