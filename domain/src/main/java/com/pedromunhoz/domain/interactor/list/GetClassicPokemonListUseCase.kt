package com.pedromunhoz.domain.interactor.list

import com.pedromunhoz.domain.executor.PostExecutionThread
import com.pedromunhoz.domain.interactor.FlowableUseCase
import com.pedromunhoz.domain.model.PokemonClassic
import com.pedromunhoz.domain.repository.Repository
import io.reactivex.Flowable

open class GetClassicPokemonListUseCase (
    private val repository: Repository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<MutableList<PokemonClassic>, Unit?>(postExecutionThread) {

    override fun buildUseCaseFlowable(params: Unit?): Flowable<MutableList<PokemonClassic>> {
        return repository.getClassicPokemonList()
    }
}