package com.pedromunhoz.domain.interactor.favorite

import com.pedromunhoz.domain.executor.PostExecutionThread
import com.pedromunhoz.domain.interactor.CompletableUseCase
import com.pedromunhoz.domain.model.FavoritePokemon
import com.pedromunhoz.domain.repository.Repository
import io.reactivex.Completable

open class UpdateFavoriteUseCase(
    private val repository: Repository,
    postExecutionThread: PostExecutionThread
): CompletableUseCase<UpdateFavoriteUseCase.Params>(postExecutionThread) {

    override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null) throw IllegalArgumentException("Params can't be null")
        return repository.updateFavorite(params.favoritePokemon)
    }

    data class Params(
        val favoritePokemon: FavoritePokemon
    )
}