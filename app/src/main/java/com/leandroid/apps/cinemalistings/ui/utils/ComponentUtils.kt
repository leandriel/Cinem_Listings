package com.leandroid.apps.cinemalistings.ui.utils

import android.content.Context
import android.widget.Toast

class ComponentUtils {
    companion object {
        fun showToast(context: Context, text: String) {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        }
    }
}