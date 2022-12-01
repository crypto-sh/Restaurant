package com.restaurant.core.utils

import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import com.restaurant.R

fun ViewDataBinding.showMessage(msg: String) {
    Snackbar.make(root, msg, Snackbar.LENGTH_LONG)
        .setTextColor(ContextCompat.getColor(root.context, R.color.white))
        .show()
}