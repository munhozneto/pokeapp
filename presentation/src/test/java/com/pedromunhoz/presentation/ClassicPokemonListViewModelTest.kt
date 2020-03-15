package com.pedromunhoz.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import com.pedromunhoz.domain.interactor.favorite.UpdateFavoriteUseCase
import com.pedromunhoz.domain.interactor.list.GetClassicPokemonListUseCase
import com.pedromunhoz.domain.model.PokemonClassic
import com.pedromunhoz.presentation.mapper.PokemonClassicMapper
import com.pedromunhoz.presentation.mapper.PokemonSpecieMapper
import com.pedromunhoz.presentation.test.DomainDataFactory
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor

@RunWith(JUnit4::class)
class ClassicPokemonListViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var getClassicPokemonListUseCase = mock<GetClassicPokemonListUseCase>()
    private var updateFavoriteUseCase = mock<UpdateFavoriteUseCase>()

    private val pokemonClassicMapper = PokemonClassicMapper(
        PokemonSpecieMapper()
    )

    private val classicPokemonListViewModel = ClassicPokemonListViewModel(
        getClassicPokemonListUseCase,
        updateFavoriteUseCase,
        pokemonClassicMapper
    )

    @Captor
    private val getClassicPokemonListCaptor = argumentCaptor<((MutableList<PokemonClassic>) -> Unit)>()

    @Captor
    private val getClassicPokemonListCaptorError = argumentCaptor<((Throwable) -> Unit)>()

    @Captor
    private val updateFavoritePokemonCaptorError = argumentCaptor<((Throwable) -> Unit)>()

    @Test
    fun `fetch classic pokemon list should execute one time`() {
        classicPokemonListViewModel.fetchClassicPokemonList()
        verify(getClassicPokemonListUseCase, times(1))
            .execute(eq(null), any(), any(), eq(null))
    }

    @Test
    fun `fetch classic pokemon list should returns success`() {
        val classicPokemonList = DomainDataFactory.makeClassicPokemonList(4)

        classicPokemonListViewModel.fetchClassicPokemonList()

        verify(getClassicPokemonListUseCase, times(1))
            .execute(eq(null), getClassicPokemonListCaptor.capture(), any(), eq(null))

        getClassicPokemonListCaptor.firstValue.invoke(classicPokemonList)

        assertEquals(
            ViewState.Status.SUCCESS,
            classicPokemonListViewModel.getState().value?.status
        )
    }

    @Test
    fun `fetch classic pokemon list should returns data`() {
        val classicPokemonList = DomainDataFactory.makeClassicPokemonList(4)
        val classicPokemonBindingList = classicPokemonList.map {
            pokemonClassicMapper.fromDomain(it)
        }

        classicPokemonListViewModel.fetchClassicPokemonList()

        verify(getClassicPokemonListUseCase, times(1))
            .execute(eq(null), getClassicPokemonListCaptor.capture(), any(), eq(null))

        getClassicPokemonListCaptor.firstValue.invoke(classicPokemonList)

        assertEquals(
            classicPokemonBindingList,
            classicPokemonListViewModel.getState().value?.data
        )
    }

    @Test
    fun `fetch classic pokemon list should returns error`() {
        classicPokemonListViewModel.fetchClassicPokemonList()
        verify(getClassicPokemonListUseCase, times(1))
            .execute(eq(null), any(), getClassicPokemonListCaptorError.capture(), eq(null))
        getClassicPokemonListCaptorError.firstValue.invoke(RuntimeException())

        assertEquals(
            ViewState.Status.ERROR,
            classicPokemonListViewModel.getState().value?.status
        )
    }

    @Test
    fun `update favorite pokemon list should execute one time`() {
        val pokemonClassicBinding = pokemonClassicMapper.fromDomain(
            DomainDataFactory.makePokemonClassic()
        )

        classicPokemonListViewModel.updateFavorite(
            pokemonClassicBinding
        )
        verify(updateFavoriteUseCase, times(1))
            .execute(any(), any(), any())
    }

    @Test
    fun `update favorite pokemon list should returns success`() {
        val pokemonClassicBinding = pokemonClassicMapper.fromDomain(
            DomainDataFactory.makePokemonClassic()
        )

        classicPokemonListViewModel.updateFavorite(
            pokemonClassicBinding
        )
        verify(updateFavoriteUseCase, times(1))
            .execute(any(), any(), updateFavoritePokemonCaptorError.capture())
        updateFavoritePokemonCaptorError.firstValue.invoke(RuntimeException())

        assertEquals(
            ViewState.Status.ERROR,
            classicPokemonListViewModel.getUpdateFavoriteEvent().value?.status
        )
    }
}