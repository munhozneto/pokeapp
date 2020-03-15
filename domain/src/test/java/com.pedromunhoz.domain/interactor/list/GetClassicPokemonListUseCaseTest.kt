package com.pedromunhoz.domain.interactor.list

import com.nhaarman.mockito_kotlin.whenever
import com.pedromunhoz.domain.test.DomainDataFactory
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
class GetClassicPokemonListUseCaseTest {

    @Mock
    lateinit var repository: Repository

    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    private lateinit var getClassicPokemonListUseCase: GetClassicPokemonListUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getClassicPokemonListUseCase = GetClassicPokemonListUseCase(repository, postExecutionThread)
    }

    @Test
    fun `get classic pokemon list should complete`() {
        val classicPokemonList = DomainDataFactory.makeClassicPokemonList(4)

        whenever(repository.getClassicPokemonList()).thenReturn(Flowable.just(classicPokemonList))

        val testObserver = getClassicPokemonListUseCase.buildUseCaseFlowable().test()
        testObserver.assertComplete()
    }

    @Test
    fun `get classic pokemon list should returns data`() {
        val classicPokemonList = DomainDataFactory.makeClassicPokemonList(4)

        whenever(repository.getClassicPokemonList()).thenReturn(Flowable.just(classicPokemonList))

        val testObserver = getClassicPokemonListUseCase.buildUseCaseFlowable().test()

        testObserver.assertValue { result ->
            result.sortedBy { it.id } == classicPokemonList.sortedBy { it.id }
        }
    }
}