package com.pedromunhoz.data

import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.pedromunhoz.data_local.test.DomainDataFactory
import io.reactivex.Flowable
import io.reactivex.Maybe
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class PokeRepositoryTest {

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var localDataSource: LocalDataSource

    private lateinit var pokeRepository: PokeRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        pokeRepository = PokeRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun `should insert new favorite pokemon`() {
        val favoritePokemon = DomainDataFactory.makeFavoritePokemon()

        whenever(localDataSource.hasPokeFavorite(favoritePokemon.id)).thenReturn(Maybe.empty())

        val testObserver = pokeRepository.updateFavorite(favoritePokemon).test()
        testObserver.assertComplete()

        verify(localDataSource, times(1)).insert(favoritePokemon)
    }

    @Test
    fun `should remove a favorite pokemon`() {
        val favoritePokemon = DomainDataFactory.makeFavoritePokemon()

        whenever(localDataSource.hasPokeFavorite(favoritePokemon.id)).thenReturn(Maybe.just(true))

        val testObserver = pokeRepository.updateFavorite(favoritePokemon).test()
        testObserver.assertComplete()

        verify(localDataSource, times(1)).delete(favoritePokemon.id)
    }

    @Test
    fun `should return classic pokemon list with favorite`() {
        val favoritePokemon = DomainDataFactory.makeFavoritePokemon()

        val pokemonClassicList = DomainDataFactory.makeClassicPokemonList(1)
            .map {
                it.copy(id = favoritePokemon.id)
            }
            .toMutableList()

        whenever(remoteDataSource.getClassicPokemonList()).thenReturn(
            Flowable.just(
                pokemonClassicList
            )
        )

        whenever(localDataSource.hasPokeFavorite(favoritePokemon.id)).thenReturn(Maybe.just(true))

        val result = pokeRepository.getClassicPokemonList().blockingFirst()

        result.map {
            assertTrue(it.isFavorite)
        }
    }

    @Test
    fun `should return classic pokemon list without favorite`() {
        val favoritePokemon = DomainDataFactory.makeFavoritePokemon()

        val pokemonClassicList = DomainDataFactory.makeClassicPokemonList(1)
            .map {
                it.copy(id = favoritePokemon.id)
            }
            .toMutableList()

        whenever(remoteDataSource.getClassicPokemonList()).thenReturn(Flowable.just(pokemonClassicList))

        whenever(localDataSource.hasPokeFavorite(favoritePokemon.id)).thenReturn(Maybe.empty())

        val result = pokeRepository.getClassicPokemonList().blockingFirst()

        result.map {
            assertFalse(it.isFavorite)
        }
    }
}