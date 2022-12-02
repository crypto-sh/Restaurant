package com.restaurant.core.utils

import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import com.restaurant.R

fun ViewDataBinding.showMessage(msg: String) {
    Snackbar.make(root, msg, Snackbar.LENGTH_LONG)
        .setTextColor(ContextCompat.getColor(root.context, R.color.white))
        .show()
}


fun MenuItem?.handleSearchView(onTextChanged: (String) -> Unit) {
    if (this == null)
        return
    val searchView = actionView as SearchView?
    searchView?.setOnCloseListener {
        onTextChanged("")
        searchView.clearFocus()
        return@setOnCloseListener false
    }
    searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean = false
        override fun onQueryTextChange(newText: String): Boolean {
            onTextChanged(newText)
            return true
        }
    })
}