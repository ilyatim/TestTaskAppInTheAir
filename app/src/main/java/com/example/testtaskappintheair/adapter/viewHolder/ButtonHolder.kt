package com.example.testtaskappintheair.adapter.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.testtaskappintheair.R
import com.example.testtaskappintheair.model.RecyclerViewCell

class ButtonHolder(
    layoutInflater: LayoutInflater,
    parent: ViewGroup,
    clickListener: View.OnClickListener
) : AbsViewHolder(layoutInflater.inflate(R.layout.recycler_item_button, parent, false)) {

    private val submitButton: Button
            = itemView.findViewById(R.id.submit_fragment_recycler_submit_button)

    init {
        submitButton.setOnClickListener(clickListener)
    }

    override fun bind(cell: RecyclerViewCell) {

    }
}