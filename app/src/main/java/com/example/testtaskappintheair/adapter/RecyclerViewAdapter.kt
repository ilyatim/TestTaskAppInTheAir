package com.example.testtaskappintheair.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskappintheair.R
import com.example.testtaskappintheair.model.RateCell
import com.example.testtaskappintheair.adapter.viewHolder.AbsViewHolder
import com.example.testtaskappintheair.adapter.viewHolder.ButtonHolder
import com.example.testtaskappintheair.adapter.viewHolder.FeedbackHolder
import com.example.testtaskappintheair.adapter.viewHolder.RateHolder
import java.lang.IllegalStateException

class RecyclerViewAdapter(
    private var items: List<RateCell>,
    private val inflate: LayoutInflater)
    : RecyclerView.Adapter<AbsViewHolder>() {

    private val viewTypeValues = ViewType.values()
    private val RateCell.viewType: ViewType
        get() = when (this) {
            is RateCell.RateDataClassModel -> ViewType.RATE
            is RateCell.FeedbackDataClassModel -> ViewType.FEEDBACK
            is RateCell.ButtonDataClassModel -> ViewType.BUTTON
        }

    fun update(items: List<RateCell>) {
        this.items = items
        this.notifyDataSetChanged()
    }

    private fun getItem(position: Int) = items[position]

    override fun getItemViewType(position: Int): Int = getItem(position).viewType.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsViewHolder {
        return when (viewTypeValues[viewType]) {
            ViewType.RATE -> RateHolder(parent)
            ViewType.FEEDBACK -> FeedbackHolder(inflate, parent)
            ViewType.BUTTON -> ButtonHolder(parent)
        }

    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AbsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private enum class ViewType {
        RATE,
        FEEDBACK,
        BUTTON,
    }
}