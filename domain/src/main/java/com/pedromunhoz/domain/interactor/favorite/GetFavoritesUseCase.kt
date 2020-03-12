package com.pedromunhoz.domain.interactor.favorite

import com.pedromunhoz.domain.executor.PostExecutionThread
import com.pedromunhoz.domain.interactor.MaybeUseCase
import com.pedromunhoz.domain.model.FavoritePokemon
import com.pedromunhoz.domain.repository.Repository
import io.reactivex.Maybe

open class GetFavoritesUseCase(
    private val repository: Repository,
    postExecutionThread: PostExecutionThread
) : MaybeUseCase<MutableList<FavoritePokemon>, Unit>(postExecutionThread) {

    override fun buildUseCaseMaybe(params: Unit?): Maybe<MutableList<FavoritePokemon>> {
        return repository.getPokeFavorites()
    }
}