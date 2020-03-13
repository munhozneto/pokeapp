package com.pedromunhoz.pokeapp.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pedromunhoz.pokeapp.R
import com.pedromunhoz.pokeapp.extension.inflate
import com.pedromunhoz.pokeapp.extension.loadUrl
import com.pedromunhoz.presentation.model.PokemonClassicBinding
import kotlinx.android.synthetic.main.item_poke_list.view.*
import java.util.function.UnaryOperator


class ClassicPokemonListAdapter(
    private var pokeList: MutableList<PokemonClassicBinding>,
    private val onClickItem: (Int) -> Unit,
    private val onFavoriteItem: (PokemonClassicBinding, Int) -> Unit
) : RecyclerView.Adapter<ClassicPokemonListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_poke_list), onClickItem, onFavoriteItem)

    override fun getItemCount() = pokeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pokeList[position], position)
    }

    fun updateList(pokeList: MutableList<PokemonClassicBinding>) {
        this.pokeList.addAll(pokeList)
        notifyDataSetChanged()
    }

    fun updateItem(pokemonId: Int) {
        val pokemonClassicBinding = pokeList.first {
            it.id == pokemonId
        }

        val isFavorite = !pokemonClassicBinding.isFavorite
        val pokemonClassicCopy = pokemonClassicBinding.copy(isFavorite = isFavorite)
        val position = pokeList.indexOf(pokemonClassicBinding)

        pokeList.removeAt(position)
        pokeList.add(position, pokemonClassicCopy)

        notifyItemChanged(position)
    }

    class ViewHolder(
        itemView: View,
        private val onClickItem: (Int) -> Unit,
        private val onFavoriteItem: (PokemonClassicBinding, Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(pokemon: PokemonClassicBinding, position: Int) = with(itemView) {
            with(pokemon) {
                itemPokeListContainer.setOnClickListener {
                    onClickItem(id)
                }
                itemPokeListFavoriteImageView.setOnClickListener {
                    onFavoriteItem(this, position)
                }

                val imageUrl = context.getString(R.string.image_url, pokemon.id.toString())

                itemPokeListImageVIew.loadUrl(imageUrl)
                itemPokeListTitleTextView.text = pokemonSpecie.name

                if (isFavorite) {
                    itemPokeListFavoriteImageView.setImageResource(R.drawable.ic_favorite_red_48dp)
                } else {
                    itemPokeListFavoriteImageView.setImageResource(R.drawable.ic_favorite_grey_48dp)
                }
            }
        }
    }
}