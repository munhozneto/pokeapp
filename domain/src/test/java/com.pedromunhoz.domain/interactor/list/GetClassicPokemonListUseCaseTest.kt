package com.pedromunhoz.domain.interactor.list

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.pedromunhoz.data_local.test.DomainDataFactory
import com.pedromunhoz.domain.executor.PostExecutionThread
import com.pedromunhoz.domain.repository.Repository
import io.reactivex.Flowable
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetClassicPokemonListUseCaseTest {

    @Mock
    lateinit var repository: Repository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    lateinit var getClassicPokemonListUseCase: GetClassicPokemonListUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getClassicPokemonListUseCase = GetClassicPokemonListUseCase(repository, postExecutionThread)
    }

    @Test
    fun `Get classic pokemon list should complete`() {
        val classicPokemonList = DomainDataFactory.makeClassicPokemonList(4)

        whenever(repository.getClassicPokemonList(any())).thenReturn(Flowable.just(classicPokemonList))

        val testObserver = getClassicPokemonListUseCase.buildUseCaseFlowable(any()).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Get classic pokemon list without pokedex id should returns exception`() {
        val classicPokemonList = DomainDataFactory.makeClassicPokemonList(2)

        whenever(repository.getClassicPokemonList(any())).thenReturn(Flowable.just(classicPokemonList))

        getClassicPokemonListUseCase.buildUseCaseFlowable().test()
    }

    @Test
    fun `Get classic pokemon list should returns data`() {
        val classicPokemonList = DomainDataFactory.makeClassicPokemonList(4)

        whenever(repository.getClassicPokemonList(any())).thenReturn(Flowable.just(classicPokemonList))

        val testObserver = getClassicPokemonListUseCase.buildUseCaseFlowable(any()).test()

        testObserver.assertValue { result ->
            result.sortedBy { it.id } == classicPokemonList.sortedBy { it.id }
        }
    }
}