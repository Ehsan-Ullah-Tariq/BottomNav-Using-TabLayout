package com.android.crazyskins.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snackBar(message: String, anchorView: View?, action: (() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    action?.let {
        snackBar.setAction("Retry") {
            it()
        }
    }
    anchorView?.let {
        snackBar.anchorView = it
    }
    snackBar.show()
}