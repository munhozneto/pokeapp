package com.pedromunhoz.domain.interactor.detail

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.pedromunhoz.data_local.test.DomainDataFactory
import com.pedromunhoz.domain.executor.PostExecutionThread
import com.pedromunhoz.domain.repository.Repository
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class CatchPokemonUseCaseTest {

    @Mock
    lateinit var repository: Repository

    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    private lateinit var catchPokemonUseCase: CatchPokemonUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        catchPokemonUseCase = CatchPokemonUseCase(repository, postExecutionThread)
    }

    @Test
    fun `Get pokemon details should complete`() {
        val pokemonDetail = DomainDataFactory.makePokemon()

        whenever(repository.catchPokemon(any())).thenReturn(Flowable.just(pokemonDetail))

        val testObserver = catchPokemonUseCase.buildUseCaseFlowable(any()).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Get pokemon details without id should returns exception`() {
        val pokemonDetail = DomainDataFactory.makePokemon()

        whenever(repository.catchPokemon(any())).thenReturn(Flowable.just(pokemonDetail))

        catchPokemonUseCase.buildUseCaseFlowable().test()
    }

    @Test
    fun `Get pokemon details should returns data`() {
        val pokemonDetail = DomainDataFactory.makePokemon()

        whenever(repository.catchPokemon(any())).thenReturn(Flowable.just(pokemonDetail))

        val testObserver = catchPokemonUseCase.buildUseCaseFlowable(any()).test()
        testObserver.assertValue(pokemonDetail)
    }
}