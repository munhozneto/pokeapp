package com.pedromunhoz.pokeapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pedromunhoz.pokeapp.R
import kotlinx.android.synthetic.main.activity_main.*

class DetailActivity : AppCompatActivity() {

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
        setSupportActionBar(toolbar)
    }

}