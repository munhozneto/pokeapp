package com.pedromunhoz.data_remote

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.pedromunhoz.data_remote.mapper.PokemonClassicMapper
import com.pedromunhoz.data_remote.model.ClassicPokemonListResponse
import com.pedromunhoz.data_remote.service.PokeApiService
import com.pedromunhoz.data_remote.test.RemoteDataFactory
import com.pedromunhoz.domain.model.PokemonClassic
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PokeRemoteDataSourceTest {
    private val service = mock<PokeApiService>()
    private val remote = PokeRemoteDataSource(service)

    @Test
    fun `should assert complete when get classic pokemon list`() {
        stubPokeApiServiceListClassicPokemon(
            Flowable.just(
                RemoteDataFactory.makeClassicPokemonList(
                    1
                )
            )
        )
        val testObserver = remote.getClassicPokemonList(any()).test()
        testObserver.assertComplete()
    }

    @Test
    fun `should call server`() {
        stubPokeApiServiceListClassicPokemon(
            Flowable.just(
                RemoteDataFactory.makeClassicPokemonList(
                    1
                )
            )
        )
        remote.getClassicPokemonList(any()).test()
        verify(service).listClassicPokemons(any())
    }

    @Test
    fun `should returns data`() {
        val remotePokemonClassicList =
            RemoteDataFactory.makeClassicPokemonList(
                5
            )
        stubPokeApiServiceListClassicPokemon(
            Flowable.just(remotePokemonClassicList)
        )

        val domainPokemonClassicList = mutableListOf<PokemonClassic>()

        remotePokemonClassicList.pokemonClassicList.forEach {
            val pokemonClassic = PokemonClassicMapper.parse(it)
            domainPokemonClassicList.add(pokemonClassic)
        }

        val testObserver = remote.getClassicPokemonList(any()).test()

        testObserver.assertValue { result ->
            result.sortedBy { it.id } == domainPokemonClassicList.sortedBy { it.id }
        }
    }

    private fun stubPokeApiServiceListClassicPokemon(flowable: Flowable<ClassicPokemonListResponse>) {
        whenever(
            service.listClassicPokemons(any())
        ).thenReturn(flowable)
    }
}