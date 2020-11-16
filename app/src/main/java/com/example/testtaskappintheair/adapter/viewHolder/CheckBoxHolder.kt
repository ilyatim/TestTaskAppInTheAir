package com.example.testtaskappintheair.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RatingBar
import android.widget.TextView
import com.example.testtaskappintheair.R
import com.example.testtaskappintheair.adapter.callback.OnCheckBoxChangeListenerCallback
import com.example.testtaskappintheair.model.RecyclerViewCell

class CheckBoxHolder(
    layoutInflater: LayoutInflater,
    parent: ViewGroup,
    onCheckBoxChangeListener: OnCheckBoxChangeListenerCallback
) : AbsViewHolder(layoutInflater.inflate(R.layout.recycler_item_rate_with_check_box, parent, false)) {

    private val checkBox: CheckBox = itemView.findViewById(R.id.submit_fragment_recycler_food_check_box)
    private val titleRatingBar: TextView = itemView.findViewById(R.id.submit_fragment_recycler_rating_check_box_text_view)
    private val ratingBar: RatingBar = itemView.findViewById(R.id.submit_fragment_recycler_check_box_rating_bar)

    init {
        checkBox.setOnCheckedChangeListener { button, checked ->
            onCheckBoxChangeListener.onStateChange(adapterPosition, ratingBar.rating.toInt(), checked, ratingBar, checkBox)
        }
        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            onCheckBoxChangeListener.onStateChange(adapterPosition, rating.toInt(), checkBox.isChecked, ratingBar, checkBox)
        }
    }

    override fun bind(cell: RecyclerViewCell) {
        val radioButtonCell = cell as RecyclerViewCell.RateWithCheckBoxDataClass
        checkBox.text = radioButtonCell.subtitle
        checkBox.isChecked = radioButtonCell.checked
        titleRatingBar.text = radioButtonCell.title
        ratingBar.rating = radioButtonCell.rate.toFloat()
    }
}