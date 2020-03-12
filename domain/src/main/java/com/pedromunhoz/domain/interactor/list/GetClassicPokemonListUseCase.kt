package com.pedromunhoz.domain.interactor.list

import com.pedromunhoz.domain.executor.PostExecutionThread
import com.pedromunhoz.domain.interactor.FlowableUseCase
import com.pedromunhoz.domain.model.PokemonClassic
import com.pedromunhoz.domain.repository.Repository
import io.reactivex.Flowable

open class GetClassicPokemonListUseCase (
    private val repository: Repository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<MutableList<PokemonClassic>, Int>(postExecutionThread) {

    override fun buildUseCaseFlowable(params: Int?): Flowable<MutableList<PokemonClassic>> {
        if (params == null) throw IllegalArgumentException("You must inform the pokedex id.")
        return repository.getClassicPokemonList(params)
    }
}