package com.ssafy.silencelake.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(message: String){
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}