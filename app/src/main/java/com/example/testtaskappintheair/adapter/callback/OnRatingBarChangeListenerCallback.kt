package com.example.testtaskappintheair.adapter.callback

import android.widget.RatingBar

interface OnRatingBarChangeListenerCallback {
    fun onRatingBarChange(pos: Int, ratingBar: RatingBar, rating: Float, fromUser: Boolean)
}