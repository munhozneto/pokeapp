package com.pedromunhoz.pokeapp.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.pedromunhoz.pokeapp.R
import com.pedromunhoz.pokeapp.extension.hide

open class BaseActivity : AppCompatActivity() {

    protected lateinit var progressContainer: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupProgressView()
    }

    private fun setupProgressView() {
        val layout = findViewById<View>(android.R.id.content).rootView as ViewGroup
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val progressBar = inflater.inflate(R.layout.view_progress_dialog, null)
        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)

        progressContainer = RelativeLayout(this)
        progressContainer.addView(progressBar)
        progressContainer.gravity = Gravity.CENTER
        progressContainer.hide()

        layout.addView(progressContainer, params)
    }
}