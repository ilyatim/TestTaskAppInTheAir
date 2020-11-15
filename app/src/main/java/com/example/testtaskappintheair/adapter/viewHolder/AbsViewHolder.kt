package com.example.testtaskappintheair.adapter.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskappintheair.model.RecyclerViewCell

abstract class AbsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(cell: RecyclerViewCell)
}