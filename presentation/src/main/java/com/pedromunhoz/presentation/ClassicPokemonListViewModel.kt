package com.pedromunhoz.presentation

import androidx.lifecycle.*
import com.pedromunhoz.domain.interactor.favorite.UpdateFavoriteUseCase
import com.pedromunhoz.domain.interactor.list.GetClassicPokemonListUseCase
import com.pedromunhoz.domain.model.FavoritePokemon
import com.pedromunhoz.presentation.mapper.PokemonClassicMapper
import com.pedromunhoz.presentation.model.PokemonClassicBinding

class ClassicPokemonListViewModel(
    private val getClassicPokemonListUseCase: GetClassicPokemonListUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
    private val pokemonClassicMapper: PokemonClassicMapper
) : ViewModel(), LifecycleObserver {

    private val state: MutableLiveData<ViewState<MutableList<PokemonClassicBinding>>> = MutableLiveData()
    private val updateFavoriteEvent: MutableLiveData<ViewState<PokemonClassicBinding>> = MutableLiveData()

    fun getState(): LiveData<ViewState<MutableList<PokemonClassicBinding>>> {
        return state
    }

    fun getUpdateFavoriteEvent(): LiveData<ViewState<PokemonClassicBinding>> {
        return updateFavoriteEvent
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchIfNeeded() {
        if (state.value == null) {
            fetchClassicPokemonList()
        }
    }

    fun fetchClassicPokemonList() {
        state.postValue(ViewState(ViewState.Status.LOADING))
        getClassicPokemonListUseCase.execute(
            null,
            { classicPokemonList ->
                val classicPokemonListBinding = classicPokemonList.map {
                    pokemonClassicMapper.fromDomain(it)
                }.toMutableList()

                state.postValue(
                    ViewState(ViewState.Status.SUCCESS, classicPokemonListBinding)
                )
            },
            { e ->
                state.postValue(ViewState(ViewState.Status.ERROR, error = e))
            }
        )
    }

    fun updateFavorite(pokemon: PokemonClassicBinding) {
        val favoritePokemon = FavoritePokemon(
            pokemon.id,
            pokemon.pokemonSpecie.name
        )
        updateFavoriteUseCase.execute(
            UpdateFavoriteUseCase.Params(favoritePokemon),
            {
                updateFavoriteEvent.postValue(
                    ViewState(ViewState.Status.SUCCESS, pokemon)
                )
            },
            { e ->
                updateFavoriteEvent.postValue(ViewState(ViewState.Status.ERROR, error = e))
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        getClassicPokemonListUseCase.dispose()
        updateFavoriteUseCase.dispose()
    }
}