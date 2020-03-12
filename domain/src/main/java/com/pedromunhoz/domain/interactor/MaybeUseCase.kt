package com.pedromunhoz.domain.interactor

import com.pedromunhoz.domain.executor.PostExecutionThread
import io.reactivex.Maybe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class MaybeUseCase<T, in Params> constructor(
    private val postExecutionThread: PostExecutionThread
) {
    private val disposables = CompositeDisposable()

    abstract fun buildUseCaseMaybe(params: Params? = null): Maybe<T>

    open fun execute(
        params: Params? = null,
        onNext: (T) -> Unit,
        onError: (e: Throwable) -> Unit,
        onComplete: (() -> Unit)? = null
    ) {
        val maybe = this.buildUseCaseMaybe(params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.scheduler)

        addDisposable(maybe.subscribe(
            { t: T ->
                onNext.invoke(t)
            },
            { error ->
                onError.invoke(error)
            },
            {
                onComplete?.invoke()
            }
        ))
    }

    fun dispose() {
        disposables.clear()
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}