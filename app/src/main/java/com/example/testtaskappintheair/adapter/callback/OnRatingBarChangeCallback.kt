package com.example.testtaskappintheair.adapter.callback

import android.widget.RatingBar

interface OnRatingBarChangeCallback {
    fun onRatingBarChange(pos: Int, rating: Float)
}