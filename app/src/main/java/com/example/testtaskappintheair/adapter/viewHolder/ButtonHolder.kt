package com.example.testtaskappintheair.adapter.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.testtaskappintheair.R
import com.example.testtaskappintheair.model.RecyclerViewCell

class ButtonHolder(
    layoutInflater: LayoutInflater,
    parent: ViewGroup
) : AbsViewHolder(layoutInflater.inflate(R.layout.recycler_item_button, parent, false)),
    View.OnClickListener {

    private val submitButton: Button
            = itemView.findViewById(R.id.submit_fragment_recycler_submit_button)

    init {
        itemView.setOnClickListener(this)
    }

    override fun bind(cell: RecyclerViewCell) {

    }

    override fun onClick(p0: View?) {

    }

}