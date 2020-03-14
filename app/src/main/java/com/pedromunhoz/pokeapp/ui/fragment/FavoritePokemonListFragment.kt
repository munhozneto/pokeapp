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
import com.pedromunhoz.pokeapp.ui.adapter.FavoritePokemonListAdapter
import com.pedromunhoz.presentation.FavoritePokemonListViewModel
import com.pedromunhoz.presentation.ViewState
import com.pedromunhoz.presentation.model.FavoritePokemonBinding
import kotlinx.android.synthetic.main.fragment_pokemon_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritePokemonListFragment : BaseFragment() {

    private val viewModel: FavoritePokemonListViewModel by viewModel()

    private lateinit var favoritePokemonListAdapter: FavoritePokemonListAdapter

    companion object {
        fun newInstance() = FavoritePokemonListFragment()
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
                        favoritePokemonListAdapter.removeFavoritePokemon(pokemonId = it.id)
                    }
                }
            }
        })
    }

    private fun handleState(state: ViewState<MutableList<FavoritePokemonBinding>>?) {
        when (state?.status) {
            ViewState.Status.LOADING -> {
                progressContainer?.show()
            }
            ViewState.Status.SUCCESS -> {
                progressContainer?.hide()

                state.data?.let {
                    if (it.size > 0) {
                        favoritePokemonListAdapter.updateList(it)
                    } else {
                        viewEmptyState.show()
                    }
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
        favoritePokemonListAdapter = FavoritePokemonListAdapter(
            favoriteList = mutableListOf(),
            onClickItem = {
                startActivity(DetailActivity.newInstance(it, context))
            },
            onFavoriteItem = { pokemon ->
                updateFavorite(pokemon)
            })

        val linearLayoutManager = LinearLayoutManager(context)
        pokemonListRecyclerView.setHasFixedSize(true)
        pokemonListRecyclerView.layoutManager = linearLayoutManager
        pokemonListRecyclerView.isNestedScrollingEnabled = false
        pokemonListRecyclerView.adapter = favoritePokemonListAdapter
    }

    private fun updateFavorite(pokemon: FavoritePokemonBinding) {
        viewModel.updateFavorite(pokemon)
    }
}