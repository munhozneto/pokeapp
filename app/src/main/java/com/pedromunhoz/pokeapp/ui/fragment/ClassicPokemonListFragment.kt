package com.pedromunhoz.pokeapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedromunhoz.pokeapp.R
import com.pedromunhoz.pokeapp.extension.hide
import com.pedromunhoz.pokeapp.extension.show
import com.pedromunhoz.pokeapp.ui.activity.DetailActivity
import com.pedromunhoz.pokeapp.ui.adapter.ClassicPokemonListAdapter
import com.pedromunhoz.presentation.ClassicPokemonListViewModel
import com.pedromunhoz.presentation.ViewState
import com.pedromunhoz.presentation.model.PokemonClassicBinding
import kotlinx.android.synthetic.main.fragment_pokemon_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClassicPokemonListFragment : BaseFragment() {

    private val viewModel: ClassicPokemonListViewModel by viewModel()

    private lateinit var pokeListAdapter: ClassicPokemonListAdapter

    companion object {
        fun newInstance() = ClassicPokemonListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pokemon_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewModel.getState().observe(viewLifecycleOwner, Observer { newState ->
            newState?.let {
                handleState(it)
            }
        })
        viewModel.getUpdateFavoriteEvent().observe(viewLifecycleOwner, Observer { newState ->
            newState?.let { state ->
                if (state.status == ViewState.Status.SUCCESS) {
                    state.data?.let {
                        pokeListAdapter.updateItem(pokemonId = it.id)
                    }
                }
            }
        })
    }

    private fun handleState(state: ViewState<MutableList<PokemonClassicBinding>>?) {
        when (state?.status) {
            ViewState.Status.LOADING -> {
                progressContainer?.show()
            }
            ViewState.Status.SUCCESS -> {
                progressContainer?.hide()
                state.data?.let {
                    pokeListAdapter.updateList(it)
                } ?: run {
                    viewEmptyState.show()
                }
            }
            ViewState.Status.ERROR -> {
                progressContainer?.hide()
                state.error?.let {
                    viewError.show()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        pokeListAdapter = ClassicPokemonListAdapter(
            pokeList = mutableListOf(),
            onClickItem = {
                startActivity(DetailActivity.newInstance(it, context))
            },
            onFavoriteItem = { pokemon, _ ->
                updateFavorite(pokemon)
            })

        val linearLayoutManager = LinearLayoutManager(context)
        pokemonListRecyclerView.setHasFixedSize(true)
        pokemonListRecyclerView.layoutManager = linearLayoutManager
        pokemonListRecyclerView.isNestedScrollingEnabled = false
        pokemonListRecyclerView.adapter = pokeListAdapter
    }

    private fun updateFavorite(pokemon: PokemonClassicBinding) {
        viewModel.updateFavorite(pokemon)
    }
}