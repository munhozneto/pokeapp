package com.pedromunhoz.presentation

import androidx.lifecycle.*
import com.pedromunhoz.domain.interactor.favorite.UpdateFavoriteUseCase
import com.pedromunhoz.domain.interactor.list.GetClassicPokemonListUseCase
import com.pedromunhoz.presentation.mapper.FavoritePokemonMapper
import com.pedromunhoz.presentation.mapper.PokemonClassicMapper
import com.pedromunhoz.presentation.model.FavoritePokemonBinding
import com.pedromunhoz.presentation.model.PokemonClassicBinding

class ClassicPokemonListViewModel(
    private val getClassicPokemonListUseCase: GetClassicPokemonListUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
    private val pokemonClassicMapper: PokemonClassicMapper,
    private val favoritePokemonMapper: FavoritePokemonMapper
) : ViewModel(), LifecycleObserver {

    private val state: MutableLiveData<ViewState<MutableList<PokemonClassicBinding>>> = MutableLiveData()
    private val updateFavoriteEvent: MutableLiveData<ViewState<Unit>> = MutableLiveData()

    fun getState(): LiveData<ViewState<MutableList<PokemonClassicBinding>>> {
        return state
    }

    fun getUpdateFavoriteEvent(): LiveData<ViewState<Unit>> {
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

    fun updateFavorite(favoritePokemonBinding: FavoritePokemonBinding) {
        val favoritePokemon = favoritePokemonMapper.toDomain(favoritePokemonBinding)
        updateFavoriteUseCase.execute(
            UpdateFavoriteUseCase.Params(favoritePokemon),
            {
                updateFavoriteEvent.postValue(
                    ViewState(ViewState.Status.SUCCESS)
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