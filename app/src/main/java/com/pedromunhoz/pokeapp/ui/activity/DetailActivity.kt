package com.pedromunhoz.pokeapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.pedromunhoz.pokeapp.R
import com.pedromunhoz.pokeapp.extension.hide
import com.pedromunhoz.pokeapp.extension.loadUrl
import com.pedromunhoz.pokeapp.extension.show
import com.pedromunhoz.presentation.PokemonDetailsViewModel
import com.pedromunhoz.presentation.ViewState
import com.pedromunhoz.presentation.model.PokemonBinding
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity() {

    private val viewModel: PokemonDetailsViewModel by viewModel()

    companion object {
        private const val POKEMON_ID_EXTRA = "pokemonId"

        private var pokemonId: Int = 0

        fun newInstance(pokemonId: Int, context: Context?): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(POKEMON_ID_EXTRA, pokemonId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(detailToolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        intent.extras?.let {
            pokemonId = it.getInt(POKEMON_ID_EXTRA)
        }

        lifecycle.addObserver(viewModel)

        initObserverState()
    }

    private fun initObserverState() {
       viewModel.pokemonId = pokemonId
        viewModel.getCatchPokemonState().observe(this, Observer { newState ->
            newState?.let {
                handleState(it)
            }
        })
    }

    private fun handleState(state: ViewState<PokemonBinding>) {
        when (state.status) {
            ViewState.Status.LOADING -> {
                progressContainer.show()
            }
            ViewState.Status.SUCCESS -> {
                progressContainer.hide()
                state.data?.let {
                    showDetail(it)
                }
            }
            ViewState.Status.ERROR -> {
                progressContainer.hide()
                state.error?.let {
                    viewError.show()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDetail(pokemon: PokemonBinding) {
        detailContent.show()
        with(pokemon) {
            detailFrontImageView.loadUrl(sprites.frontDefault)
            detailBackImageView.loadUrl(sprites.backDefault)
            detailNameTextView.text = name
            detailBaseExperienceTextView.text = baseExperience.toString()
            detailWeightTextView.text = weight.toString()
            detailHeightTextView.text = height.toString()
            detailAbilitiesTextView.text = abilities.joinToString(", ", "", "", -1) {
                it.ability.name
            }
            detailStatsTextView.text = stats.joinToString(", ", "", "", -1) {
                "${it.stat.name}: ${it.baseStat}"
            }
            detailTypesTextView.text = types.joinToString(", ", "", "", -1) {
                it.type.name
            }
        }
    }
}