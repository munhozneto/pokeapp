package com.pedromunhoz.domain.interactor.detail

import com.pedromunhoz.domain.executor.PostExecutionThread
import com.pedromunhoz.domain.interactor.FlowableUseCase
import com.pedromunhoz.domain.model.Pokemon
import com.pedromunhoz.domain.model.PokemonClassic
import com.pedromunhoz.domain.repository.Repository
import io.reactivex.Flowable

open class CatchPokemonUseCase(
    private val repository: Repository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<Pokemon?, Int>(postExecutionThread) {

    override fun buildUseCaseFlowable(params: Int?): Flowable<Pokemon?> {
        if (params == null) throw IllegalArgumentException("You must inform the pokemon id.")
        return repository.catchPokemon(params)
    }
}