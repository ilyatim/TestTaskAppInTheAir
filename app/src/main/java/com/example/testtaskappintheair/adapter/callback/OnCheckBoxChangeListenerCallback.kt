package com.example.testtaskappintheair.adapter.callback

import android.widget.CheckBox
import android.widget.RatingBar

interface OnCheckBoxChangeListenerCallback {
    fun onStateChange(pos: Int, rating: Int, foodExist: Boolean, ratingBar: RatingBar, checkBox: CheckBox)
}