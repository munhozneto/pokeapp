package com.pedromunhoz.pokeapp.di

import com.pedromunhoz.domain.interactor.detail.CatchPokemonUseCase
import com.pedromunhoz.domain.interactor.favorite.GetFavoritesUseCase
import com.pedromunhoz.domain.interactor.favorite.UpdateFavoriteUseCase
import com.pedromunhoz.domain.interactor.list.GetClassicPokemonListUseCase
import org.koin.dsl.module

val domainModule = module {
    single {
        CatchPokemonUseCase(
            repository =  get(),
           postExecutionThread =  get()
        )
    }
    single {
        GetFavoritesUseCase(
            repository =  get(),
            postExecutionThread =  get()
        )
    }
    single {
        UpdateFavoriteUseCase(
            repository =  get(),
            postExecutionThread =  get()
        )
    }
    single {
        GetClassicPokemonListUseCase(
            repository =  get(),
            postExecutionThread =  get()
        )
    }
}