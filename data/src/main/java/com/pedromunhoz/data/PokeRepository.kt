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
            .flatMapSingle { setFavoriteIfNeeded(it) }
            .toList()
    }

    override fun catchPokemon(id: Int): Single<Pokemon?> {
        return remoteDataSource.catchPokemon(id)
    }

    override fun updateFavorite(favoritePokemon: FavoritePokemon): Completable {
       return localDataSource.hasPokeFavorite(favoritePokemon.id)
           .switchIfEmpty(Maybe.fromCallable {
               localDataSource.insert(favoritePokemon).test()
               null
           })
           .map {
               localDataSource.delete(favoritePokemon.id).test()
           }
           .flatMapCompletable { Completable.complete() }
    }

    override fun getPokeFavorites(): Maybe<MutableList<FavoritePokemon>> {
        return localDataSource.getPokeFavorites()
    }

    private fun setFavoriteIfNeeded(pokemonClassic: PokemonClassic): Single<PokemonClassic> {
        return localDataSource.hasPokeFavorite(pokemonClassic.id)
            .switchIfEmpty(Maybe.fromCallable { null })
            .map {
                pokemonClassic.copy(isFavorite = true)
            }
            .flatMapSingle { Single.just(pokemonClassic) }
    }
}