package com.example.testtaskappintheair.adapter.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.testtaskappintheair.R
import com.example.testtaskappintheair.model.RateCell

class ButtonHolder(
    layoutInflater: LayoutInflater,
    parent: ViewGroup
) : AbsViewHolder(layoutInflater.inflate(R.layout.recycler_item_button, parent, false)) {

    private val submitButton: Button = itemView.findViewById(R.id.submit_fragment_recycler_submit_button)

    override fun bind(cell: RateCell) {
        //
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

}