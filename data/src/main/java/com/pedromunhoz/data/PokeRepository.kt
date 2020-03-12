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

    override fun getClassicPokemonList(pokedexId: Int): Flowable<MutableList<PokemonClassic>> {
        return remoteDataSource.getClassicPokemonList(pokedexId)
            .flatMapIterable { it }
            .flatMapMaybe { setFavoriteIfNeeded(it) }
            .toList()
            .toFlowable()
    }

    override fun catchPokemon(id: Int): Flowable<Pokemon?> {
        return remoteDataSource.catchPokemon(id)
    }

    override fun updateFavorite(favoritePokemon: FavoritePokemon): Completable {
       return localDataSource.hasPokeFavorite(favoritePokemon.id)
           .defaultIfEmpty(false)
           .map { isFavorite ->
               if (isFavorite) {
                   localDataSource.delete(favoritePokemon.id)
               } else {
                   localDataSource.insert(favoritePokemon)
               }
               Completable.complete()
           }
           .flatMapCompletable { Completable.complete() }
    }

    override fun getPokeFavorites(): Maybe<MutableList<FavoritePokemon>> {
        return localDataSource.getPokeFavorites()
    }

    private fun setFavoriteIfNeeded(pokemonClassic: PokemonClassic): Maybe<PokemonClassic> {
        return localDataSource.hasPokeFavorite(pokemonClassic.id)
            .defaultIfEmpty(false)
            .map {
                pokemonClassic.copy(isFavorite = it)
            }
    }
}