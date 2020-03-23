package com.pedromunhoz.domain.interactor.favorite

import com.nhaarman.mockito_kotlin.whenever
import com.pedromunhoz.domain.test.DomainDataFactory
import com.pedromunhoz.domain.executor.PostExecutionThread
import com.pedromunhoz.domain.repository.Repository
import io.reactivex.Completable
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class UpdateFavoriteUseCaseTest {

    @Mock
    lateinit var repository: Repository

    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    private lateinit var updateFavoriteUseCase: UpdateFavoriteUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        updateFavoriteUseCase = UpdateFavoriteUseCase(repository, postExecutionThread)
    }

    @Test
    fun `update favorite pokemon should complete`() {
        val favorite = DomainDataFactory.makeFavoritePokemon()

        whenever(repository.updateFavorite(favorite)).thenReturn(Maybe.just(0))

        val testObserver = updateFavoriteUseCase.buildUseCaseMaybe(
            UpdateFavoriteUseCase.Params(favorite)
        ).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `update favorite pokemon without param should returns exception`() {
        updateFavoriteUseCase.buildUseCaseMaybe().test()
    }
}