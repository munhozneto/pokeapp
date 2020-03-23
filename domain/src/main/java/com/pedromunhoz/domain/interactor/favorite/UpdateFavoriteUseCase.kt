package com.pedromunhoz.domain.interactor.favorite

import com.pedromunhoz.domain.executor.PostExecutionThread
import com.pedromunhoz.domain.interactor.CompletableUseCase
import com.pedromunhoz.domain.interactor.MaybeUseCase
import com.pedromunhoz.domain.model.FavoritePokemon
import com.pedromunhoz.domain.repository.Repository
import io.reactivex.Completable
import io.reactivex.Maybe

open class UpdateFavoriteUseCase(
    private val repository: Repository,
    postExecutionThread: PostExecutionThread
): MaybeUseCase<Int, UpdateFavoriteUseCase.Params>(postExecutionThread) {

    override fun buildUseCaseMaybe(params: Params?): Maybe<Int> {
        if (params == null) throw IllegalArgumentException("Params can't be null")
        return repository.updateFavorite(params.favoritePokemon)
    }

    data class Params(
        val favoritePokemon: FavoritePokemon
    )
}