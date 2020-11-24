package com.example.testtaskappintheair.adapter.callback

import android.widget.CheckBox
import android.widget.RatingBar

interface OnCheckBoxChangeCallback {
    fun onCheckBoxStateChange(
        pos: Int,
        checked: Boolean,
        rating: Int,
        ratingBar: RatingBar
    )
    fun onRatingBarChange(
        pos: Int,
        rating: Int,
        foodExist: Boolean
    )
}