package com.pedromunhoz.domain.interactor.favorite

import com.nhaarman.mockito_kotlin.whenever
import com.pedromunhoz.domain.test.DomainDataFactory
import com.pedromunhoz.domain.executor.PostExecutionThread
import com.pedromunhoz.domain.repository.Repository
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetFavoritesUseCaseTest {

    @Mock
    lateinit var repository: Repository

    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    private lateinit var getFavoritesUseCase: GetFavoritesUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getFavoritesUseCase = GetFavoritesUseCase(repository, postExecutionThread)
    }

    @Test
    fun `get favorites should complete`() {
        val favorites = DomainDataFactory.makeFavoritePokemonList(3)

        whenever(repository.getPokeFavorites()).thenReturn(Maybe.just(favorites))

        val testObserver = getFavoritesUseCase.buildUseCaseMaybe().test()
        testObserver.assertComplete()
    }

    @Test
    fun `get favorites should return data`() {
        val favorites = DomainDataFactory.makeFavoritePokemonList(3)

        whenever(repository.getPokeFavorites()).thenReturn(Maybe.just(favorites))

        val testObserver = getFavoritesUseCase.buildUseCaseMaybe().test()
        testObserver.assertValue { result ->
            result.sortedBy { it.id } == favorites.sortedBy { it.id }
        }
    }
}