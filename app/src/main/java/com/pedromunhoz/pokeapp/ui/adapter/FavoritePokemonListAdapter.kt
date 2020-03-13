package com.pedromunhoz.pokeapp.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pedromunhoz.pokeapp.R
import com.pedromunhoz.pokeapp.extension.inflate
import com.pedromunhoz.pokeapp.extension.loadUrl
import com.pedromunhoz.presentation.model.FavoritePokemonBinding
import kotlinx.android.synthetic.main.item_poke_list.view.*


class FavoritePokemonListAdapter(private val favoriteList: MutableList<FavoritePokemonBinding>,
                                 private val onClickItem: (Int) -> Unit,
                                 private val onFavoriteItem: (FavoritePokemonBinding) -> Unit)
    : RecyclerView.Adapter<FavoritePokemonListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ViewHolder(parent.inflate(R.layout.item_poke_list), onClickItem, onFavoriteItem)

    override fun getItemCount() = favoriteList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favoriteList[position])
    }

    fun updateList(favoriteList: MutableList<FavoritePokemonBinding>) {
        this.favoriteList.clear()
        this.favoriteList.addAll(favoriteList)
        notifyDataSetChanged()
    }

    fun removeFavoritePokemon(pokemonId: Int) {
        val position = favoriteList.indexOfFirst {
            it.id == pokemonId
        }
        this.favoriteList.removeAt(position)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View,
                     private val onClickItem: (Int) -> Unit,
                     private val onFavoriteItem: (FavoritePokemonBinding) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bind(favoritePokemonBinding: FavoritePokemonBinding) = with(itemView) {
            with(favoritePokemonBinding) {
                itemPokeListContainer.setOnClickListener {
                    onClickItem(id)
                }
                itemPokeListFavoriteImageView.setOnClickListener {
                    onFavoriteItem(this)
                }

                val imageUrl = context.getString(R.string.image_url, favoritePokemonBinding.id.toString())

                itemPokeListImageVIew.loadUrl(imageUrl)
                itemPokeListTitleTextView.text = name
                itemPokeListFavoriteImageView.setImageResource(R.drawable.ic_favorite_red_48dp)

            }
        }
    }
}