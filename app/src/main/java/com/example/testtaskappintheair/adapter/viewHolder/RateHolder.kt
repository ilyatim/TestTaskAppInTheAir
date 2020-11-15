package com.example.testtaskappintheair.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import com.example.testtaskappintheair.R
import com.example.testtaskappintheair.model.RecyclerViewCell

class RateHolder(
    layoutInflater: LayoutInflater,
    parent: ViewGroup
) : AbsViewHolder(layoutInflater.inflate(R.layout.recycler_item_rate, parent, false)),
    RatingBar.OnRatingBarChangeListener {

    private val titleTextView: TextView = itemView.findViewById(R.id.submit_fragment_recycler_rating_text_view)
    private val ratingBar: RatingBar = itemView.findViewById(R.id.submit_fragment_recycler_rating_bar)

    override fun bind(cell: RecyclerViewCell) {
        val ratingCell = cell as RecyclerViewCell.ClassicRateDataClass
        titleTextView.text = ratingCell.title
        ratingBar.rating = ratingCell.rate.toFloat()
    }

    override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
        TODO("Not yet implemented")
    }

}