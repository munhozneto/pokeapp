package com.pedromunhoz.pokeapp.extension

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide

fun AppCompatActivity.isDisplayedByTag(fragmentTag: String): Boolean {
    val fragment = this.supportFragmentManager.findFragmentByTag(fragmentTag)
    return fragment != null && fragment.isVisible
}

fun FragmentActivity.replace(@IdRes id: Int, fragment: Fragment) {
    this.supportFragmentManager
        .beginTransaction()
        .replace(id, fragment, fragment?.javaClass?.canonicalName)
        .commit()
}

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)

fun Activity.toast(text: String?) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun ImageView.loadUrl(url: String?) {
    Glide.with(context).load(url).into(this)
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}