package com.pedromunhoz.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import com.pedromunhoz.domain.interactor.detail.CatchPokemonUseCase
import com.pedromunhoz.domain.model.Pokemon
import com.pedromunhoz.presentation.mapper.*
import com.pedromunhoz.presentation.test.DomainDataFactory
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor

@RunWith(JUnit4::class)
class PokemonDetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var catchPokemonUseCase = mock<CatchPokemonUseCase>()

    private var pokemonMapper = PokemonMapper(
        abilityMapper = AbilityMapper(AbilityItemMapper()),
        spritesMapper = SpritesMapper(),
        statMapper = StatMapper(StatItemMapper()),
        typeMapper = TypeMapper(TypeItemMapper())
    )

    private val pokemonDetailsViewModel = PokemonDetailsViewModel(
        catchPokemonUseCase,
        pokemonMapper
    )

    @Captor
    private val getPokemonDetailsCaptor = argumentCaptor<((Pokemon?) -> Unit)>()

    @Captor
    private val getPokemonDetailsCaptorError = argumentCaptor<((Throwable) -> Unit)>()

    @Test
    fun `fetch pokemon details should execute one time`() {
        pokemonDetailsViewModel.fetchPokemonDetails()
        verify(catchPokemonUseCase, times(1))
            .execute(any(), any(), any(), eq(null))
    }

    @Test
    fun `fetch pokemon details should returns success`() {
        val pokemon = DomainDataFactory.makePokemon()

        val pokemonClassicBinding = pokemonMapper.fromDomain(
            pokemon
        )

        pokemonDetailsViewModel.fetchPokemonDetails()

        verify(catchPokemonUseCase, times(1))
            .execute(any(), getPokemonDetailsCaptor.capture(), any(), eq(null))
        getPokemonDetailsCaptor.firstValue.invoke(pokemon)

        Assert.assertEquals(
            pokemonClassicBinding,
            pokemonDetailsViewModel.getCatchPokemonState().value?.data
        )
    }

    @Test
    fun `fetch pokemon details should returns error`() {
        pokemonDetailsViewModel.fetchPokemonDetails()
        verify(catchPokemonUseCase, times(1))
            .execute(any(), any(), getPokemonDetailsCaptorError.capture(), eq(null))
        getPokemonDetailsCaptorError.firstValue.invoke(RuntimeException())

        Assert.assertEquals(
            ViewState.Status.ERROR,
            pokemonDetailsViewModel.getCatchPokemonState().value?.status
        )
    }
}