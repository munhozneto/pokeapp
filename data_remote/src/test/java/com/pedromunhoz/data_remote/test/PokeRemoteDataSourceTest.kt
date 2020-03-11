package com.pedromunhoz.data_remote.test

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.pedromunhoz.data_remote.PokeRemoteDataSource
import com.pedromunhoz.data_remote.mapper.PokemonClassicMapper
import com.pedromunhoz.data_remote.model.ClassicPokemonListResponse
import com.pedromunhoz.data_remote.service.PokeApiService
import com.pedromunhoz.domain.model.PokemonClassic
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PokeRemoteDataSourceTest {
    private val service = mock<PokeApiService>()
    private val remote = PokeRemoteDataSource(service)

    @Test
    fun `Should assert complete when get classic pokemon list`() {
        stubPokeApiServiceListClassicPokemon(
            Single.just(RemoteDataFactory.makeClassicPokemonList(1))
        )
        val testObserver = remote.listClassicPokemons(any()).test()
        testObserver.assertComplete()
    }

    @Test
    fun `Should call server`() {
        stubPokeApiServiceListClassicPokemon(
            Single.just(RemoteDataFactory.makeClassicPokemonList(1))
        )
        remote.listClassicPokemons(any()).test()
        verify(service).listClassicPokemons(any())
    }

    @Test
    fun `Should returns data`() {
        val remotePokemonClassicList = RemoteDataFactory.makeClassicPokemonList(5)
        stubPokeApiServiceListClassicPokemon(
            Single.just(remotePokemonClassicList)
        )

        val domainPokemonClassicList = mutableListOf<PokemonClassic>()

        remotePokemonClassicList.pokemonClassicList.forEach {
            val pokemonClassic = PokemonClassicMapper.parse(it)
            domainPokemonClassicList.add(pokemonClassic)
        }

        val testObserver = remote.listClassicPokemons(any()).test()

        testObserver.assertValue { result ->
            result.sortedBy { it.id } == domainPokemonClassicList.sortedBy { it.id }
        }
    }

    private fun stubPokeApiServiceListClassicPokemon(single: Single<ClassicPokemonListResponse>) {
        whenever(
            service.listClassicPokemons(any())
        ).thenReturn(single)
    }
}