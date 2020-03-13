package com.pedromunhoz.presentation

import androidx.lifecycle.*
import com.pedromunhoz.domain.interactor.favorite.GetFavoritesUseCase
import com.pedromunhoz.domain.interactor.favorite.UpdateFavoriteUseCase
import com.pedromunhoz.presentation.mapper.FavoritePokemonMapper
import com.pedromunhoz.presentation.model.FavoritePokemonBinding

class FavoritePokemonListViewModel(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
    private val favoritePokemonMapper: FavoritePokemonMapper
) : ViewModel(), LifecycleObserver {

    private val state: MutableLiveData<ViewState<MutableList<FavoritePokemonBinding>>> = MutableLiveData()
    private val updateFavoriteEvent: MutableLiveData<ViewState<FavoritePokemonBinding>> = MutableLiveData()

    fun getState(): LiveData<ViewState<MutableList<FavoritePokemonBinding>>> {
        return state
    }

    fun getUpdateFavoriteEvent(): LiveData<ViewState<FavoritePokemonBinding>> {
        return updateFavoriteEvent
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchIfNeeded() {
        if (state.value == null) {
            fetchFavoritePokemonList()
        }
    }

    private fun fetchFavoritePokemonList() {
        state.postValue(ViewState(ViewState.Status.LOADING))
        getFavoritesUseCase.execute(
            null,
            { classicPokemonList ->
                val favoritePokemonListBinding = classicPokemonList.map {
                    favoritePokemonMapper.fromDomain(it)
                }.toMutableList()

                state.postValue(
                    ViewState(ViewState.Status.SUCCESS, favoritePokemonListBinding)
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
                    ViewState(ViewState.Status.SUCCESS, favoritePokemonBinding)
                )
            },
            { e ->
                updateFavoriteEvent.postValue(ViewState(ViewState.Status.ERROR, error = e))
            }
        )
    }
}