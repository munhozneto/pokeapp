package com.pedromunhoz.pokeapp.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pedromunhoz.pokeapp.R
import com.pedromunhoz.pokeapp.extension.isDisplayedByTag
import com.pedromunhoz.pokeapp.extension.replace
import com.pedromunhoz.pokeapp.ui.fragment.ClassicPokemonListFragment
import com.pedromunhoz.pokeapp.ui.fragment.FavoritePokemonListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        replaceFragment(ClassicPokemonListFragment.newInstance())
    }

    override fun onResume() {
        super.onResume()
        bottomNavigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_pokemons -> replaceFragment(ClassicPokemonListFragment.newInstance())
            R.id.action_favorites -> replaceFragment(FavoritePokemonListFragment.newInstance())
        }
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        fragment::class.java.canonicalName?.let {
            if (!isDisplayedByTag(it)) {
                replace(R.id.hostFragment, fragment)
            }
        }
    }
}
