package com.pedromunhoz.pokeapp.di


import com.pedromunhoz.presentation.ClassicPokemonListViewModel
import com.pedromunhoz.presentation.FavoritePokemonListViewModel
import com.pedromunhoz.presentation.PokemonDetailsViewModel
import com.pedromunhoz.presentation.mapper.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    factory {
        AbilityMapper(
            abilityItemMapper = get()
        )
    }
    factory { AbilityItemMapper() }
    factory { FavoritePokemonMapper() }
    factory {
        PokemonClassicMapper(
            pokemonSpecieMapper = get()
        )
    }
    factory {
        PokemonMapper(
            abilityMapper = get(),
            spritesMapper = get(),
            statMapper = get(),
            typeMapper = get()
        )
    }
    factory { PokemonSpecieMapper() }
    factory { SpritesMapper() }
    factory { StatItemMapper() }
    factory {
        StatMapper(
            statItemMapper = get()
        )
    }
    factory { TypeItemMapper() }
    factory {
        TypeMapper(
            typeItemMapper = get()
        )
    }
    viewModel {
        ClassicPokemonListViewModel(
            getClassicPokemonListUseCase = get(),
            updateFavoriteUseCase = get(),
            pokemonClassicMapper = get()
        )
    }
    viewModel {
        FavoritePokemonListViewModel(
            getFavoritesUseCase = get(),
            updateFavoriteUseCase = get(),
            favoritePokemonMapper = get()
        )
    }
    viewModel {
        PokemonDetailsViewModel(
            catchPokemonUseCase = get(),
            pokemonMapper = get()
        )
    }
}