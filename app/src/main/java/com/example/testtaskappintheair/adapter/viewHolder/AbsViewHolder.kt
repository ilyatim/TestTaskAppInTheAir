package com.example.testtaskappintheair.adapter.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskappintheair.model.RateCell

abstract class AbsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    abstract fun bind(cell: RateCell)
}