package com.example.nutrition_app.util

import android.view.View

fun istVisible(visible: Boolean): Int {
    return if(visible) View.VISIBLE else View.GONE
}