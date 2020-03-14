package com.pedromunhoz.presentation

import androidx.lifecycle.*
import com.pedromunhoz.domain.interactor.detail.CatchPokemonUseCase
import com.pedromunhoz.presentation.mapper.PokemonMapper
import com.pedromunhoz.presentation.model.PokemonBinding

class PokemonDetailsViewModel(
    private val catchPokemonUseCase: CatchPokemonUseCase,
    private val pokemonMapper: PokemonMapper
) : ViewModel(), LifecycleObserver {

    private val catchPokemonState: MutableLiveData<ViewState<PokemonBinding>> = MutableLiveData()
    var pokemonId: Int = 0

    fun getCatchPokemonState(): LiveData<ViewState<PokemonBinding>> {
        return catchPokemonState
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchIfNeeded() {
        if (catchPokemonState.value == null) {
            fetchPokemonDetails()
        }
    }

    private fun fetchPokemonDetails() {
        catchPokemonState.postValue(ViewState(ViewState.Status.LOADING))
        catchPokemonUseCase.execute(
            pokemonId,
            { pokemon ->
                pokemon?.let {
                    catchPokemonState.postValue(
                        ViewState(ViewState.Status.SUCCESS, pokemonMapper.fromDomain(it))
                    )
                }
            },
            { e ->
                catchPokemonState.postValue(ViewState(ViewState.Status.ERROR, error = e))
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        catchPokemonUseCase.dispose()
    }
}