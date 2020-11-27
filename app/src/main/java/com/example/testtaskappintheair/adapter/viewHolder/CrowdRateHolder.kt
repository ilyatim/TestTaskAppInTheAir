package com.example.testtaskappintheair.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import com.example.testtaskappintheair.R
import com.example.testtaskappintheair.adapter.callback.OnRatingBarChangeCallback
import com.example.testtaskappintheair.model.RecyclerViewCell

class CrowdRateHolder(
    layoutInflater: LayoutInflater,
    parent: ViewGroup,
    ratingChangeListener: OnRatingBarChangeCallback
) : AbsViewHolder(
    layoutInflater.inflate(
        R.layout.recycler_item_rate_crowd,
        parent,
        false
    )
) {

    private val titleTextView: TextView =
        itemView.findViewById(R.id.submit_fragment_recycler_rating_text_view_crowd)
    private val ratingBar: RatingBar =
        itemView.findViewById(R.id.submit_fragment_recycler_rating_bar_crowd)

    init {
        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingChangeListener.onRatingBarChange(adapterPosition, rating)
        }
    }

    override fun bind(cell: RecyclerViewCell) {
        val ratingCell = cell as RecyclerViewCell.CrowdRateDataClass
        titleTextView.text = ratingCell.title
        ratingBar.rating = ratingCell.rate.toFloat()
    }
}