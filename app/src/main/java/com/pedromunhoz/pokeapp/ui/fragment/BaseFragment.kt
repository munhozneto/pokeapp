package com.pedromunhoz.pokeapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.pedromunhoz.pokeapp.R
import com.pedromunhoz.pokeapp.extension.hide

abstract class BaseFragment : Fragment() {

    protected var progressContainer: RelativeLayout? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLoadView()
    }

    private fun setupLoadView() {
        val layout = activity?.findViewById<View>(android.R.id.content)?.rootView as ViewGroup
        val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val progressBar = inflater.inflate(R.layout.view_progress_dialog, null)
        val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)

        progressContainer = RelativeLayout(context)
        progressContainer?.addView(progressBar)
        progressContainer?.gravity = Gravity.CENTER
        progressContainer?.hide()

        layout.addView(progressContainer, params)
    }
}