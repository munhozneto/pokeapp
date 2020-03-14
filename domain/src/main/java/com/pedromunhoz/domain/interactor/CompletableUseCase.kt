package com.pedromunhoz.domain.interactor

import com.pedromunhoz.domain.executor.PostExecutionThread
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class CompletableUseCase<in Params> constructor(
    private val postExecutionThread: PostExecutionThread
) {
    private val disposables = CompositeDisposable()

    abstract fun buildUseCaseCompletable(params: Params? = null): Completable

    open fun execute(
        params: Params? = null,
        complete: () -> Unit,
        error: (e: Throwable) -> Unit
    ) {
        val completable = this.buildUseCaseCompletable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.scheduler)
        addDisposable(completable.subscribe(
            {
                complete.invoke()
            },
            {
                error.invoke(it)
            }
        ))
    }

    fun dispose() {
        disposables.dispose()
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}