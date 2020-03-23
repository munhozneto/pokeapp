package com.pedromunhoz.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import com.pedromunhoz.domain.interactor.favorite.GetFavoritesUseCase
import com.pedromunhoz.domain.interactor.favorite.UpdateFavoriteUseCase
import com.pedromunhoz.domain.model.FavoritePokemon
import com.pedromunhoz.presentation.mapper.FavoritePokemonMapper
import com.pedromunhoz.presentation.test.DomainDataFactory
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor

@RunWith(JUnit4::class)
class FavoritePokemonListViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var getFavoritesUseCase = mock<GetFavoritesUseCase>()
    private var updateFavoriteUseCase = mock<UpdateFavoriteUseCase>()

    private val favoritePokemonMapper = FavoritePokemonMapper()

    private val favoritePokemonListViewModel = FavoritePokemonListViewModel(
        getFavoritesUseCase,
        updateFavoriteUseCase,
        favoritePokemonMapper
    )

    @Captor
    private val getFavoritePokemonListCaptor = argumentCaptor<((MutableList<FavoritePokemon>) -> Unit)>()

    @Captor
    private val getFavoritePokemonListCaptorError = argumentCaptor<((Throwable) -> Unit)>()

    @Captor
    private val updateFavoritePokemonCaptor = argumentCaptor<((Int) -> Unit)>()

    @Captor
    private val updateFavoritePokemonCaptorError = argumentCaptor<((Throwable) -> Unit)>()

    @Test
    fun `fetch favorite pokemon list should execute one time`() {
        favoritePokemonListViewModel.fetchFavoritePokemonList()
        verify(getFavoritesUseCase, times(1))
            .execute(eq(null), any(), any(), eq(null))
    }

    @Test
    fun `fetch favorite pokemon list should returns success`() {
        val favoritePokemon = DomainDataFactory.makeFavoritePokemonList(4)

        favoritePokemonListViewModel.fetchFavoritePokemonList()

        verify(getFavoritesUseCase, times(1))
            .execute(eq(null), getFavoritePokemonListCaptor.capture(), any(), eq(null))

        getFavoritePokemonListCaptor.firstValue.invoke(favoritePokemon)

        Assert.assertEquals(
            ViewState.Status.SUCCESS,
            favoritePokemonListViewModel.getState().value?.status
        )
    }

    @Test
    fun `fetch favorite pokemon list should returns data`() {
        val favoritePokemonList = DomainDataFactory.makeFavoritePokemonList(4)
        val favoritePokemonBindingList = favoritePokemonList.map {
            favoritePokemonMapper.fromDomain(it)
        }

        favoritePokemonListViewModel.fetchFavoritePokemonList()

        verify(getFavoritesUseCase, times(1))
            .execute(eq(null), getFavoritePokemonListCaptor.capture(), any(), eq(null))

        getFavoritePokemonListCaptor.firstValue.invoke(favoritePokemonList)

        Assert.assertEquals(
            favoritePokemonBindingList,
            favoritePokemonListViewModel.getState().value?.data
        )
    }

    @Test
    fun `fetch favorite pokemon list should returns error`() {
        favoritePokemonListViewModel.fetchFavoritePokemonList()
        verify(getFavoritesUseCase, times(1))
            .execute(eq(null), any(), getFavoritePokemonListCaptorError.capture(), eq(null))
        getFavoritePokemonListCaptorError.firstValue.invoke(RuntimeException())

        Assert.assertEquals(
            ViewState.Status.ERROR,
            favoritePokemonListViewModel.getState().value?.status
        )
    }

    @Test
    fun `update favorite pokemon should execute one time`() {
        val favoritePokemon = DomainDataFactory.makeFavoritePokemon()

        val favoritePokemonBinding = favoritePokemonMapper.fromDomain(
            favoritePokemon
        )

        favoritePokemonListViewModel.updateFavorite(
            favoritePokemonBinding
        )
        verify(updateFavoriteUseCase, times(1))
            .execute(
                eq(UpdateFavoriteUseCase.Params(favoritePokemon)),
                updateFavoritePokemonCaptor.capture(),
                any(),
                eq(null)
            )
    }

    @Test
    fun `update favorite pokemon should returns success`() {
        val favoritePokemon = DomainDataFactory.makeFavoritePokemon()
        val favoritePokemonBinding = favoritePokemonMapper.fromDomain(
            favoritePokemon
        )

        favoritePokemonListViewModel.updateFavorite(
            favoritePokemonBinding
        )
        verify(updateFavoriteUseCase, times(1))
            .execute(
                eq(UpdateFavoriteUseCase.Params(favoritePokemon)),
                any(),
                updateFavoritePokemonCaptorError.capture(),
                eq(null)
            )
        updateFavoritePokemonCaptorError.firstValue.invoke(RuntimeException())

        Assert.assertEquals(
            ViewState.Status.ERROR,
            favoritePokemonListViewModel.getUpdateFavoriteEvent().value?.status
        )
    }
}