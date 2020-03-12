package com.pedromunhoz.data

import com.pedromunhoz.domain.model.FavoritePokemon
import com.pedromunhoz.domain.model.Pokemon
import com.pedromunhoz.domain.model.PokemonClassic
import com.pedromunhoz.domain.repository.Repository
import io.reactivex.*

class PokeRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : Repository {

    override fun getClassicPokemonList(pokedexId: Int): Single<MutableList<PokemonClassic>> {
        return remoteDataSource.getClassicPokemonList(pokedexId)
            .flatMapObservable { Observable.fromIterable(it) }
            .flatMapSingle {
                setFavoriteIfNeeded(it)
                Single.just(it)
            }
            .toList()
    }

    override fun catchPokemon(id: Int): Single<Pokemon?> {
        return remoteDataSource.catchPokemon(id)
    }

    override fun updateFavorite(favoritePokemon: FavoritePokemon): Completable {
       return localDataSource.hasPokeFavorite(favoritePokemon.id)
           .switchIfEmpty(Maybe.fromCallable {
               localDataSource.insert(favoritePokemon)
               null
           })
           .map {
               localDataSource.delete(favoritePokemon.id)
               Completable.complete()
           }
           .flatMapCompletable { Completable.complete() }
    }

    override fun getPokeFavorites(): Maybe<MutableList<FavoritePokemon>> {
        return localDataSource.getPokeFavorites()
    }

    private fun setFavoriteIfNeeded(pokemonClassic: PokemonClassic): Completable {
        return localDataSource.hasPokeFavorite(pokemonClassic.id)
            .map {
                pokemonClassic.copy(isFavorite = true)
                Completable.complete()
            }
            .flatMapCompletable { Completable.complete() }
    }
}