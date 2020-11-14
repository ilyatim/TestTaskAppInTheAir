package com.example.testtaskappintheair.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskappintheair.model.RateCell
import com.example.testtaskappintheair.adapter.viewHolder.AbsViewHolder
import com.example.testtaskappintheair.adapter.viewHolder.ButtonHolder
import com.example.testtaskappintheair.adapter.viewHolder.FeedbackHolder
import com.example.testtaskappintheair.adapter.viewHolder.RateHolder

class RecyclerViewAdapter(
    private var items: List<RateCell>,
    private val inflater: LayoutInflater)
    : RecyclerView.Adapter<AbsViewHolder>() {

    private val viewTypeValues = ViewType.values()
    private val RateCell.viewType: ViewType
        get() = when (this) {
            is RateCell.ClassicRateDataClass -> ViewType.RATE
            is RateCell.FeedbackDataClass -> ViewType.FEEDBACK
            is RateCell.MButtonDataClass -> ViewType.BUTTON
            is RateCell.MRadioButtonDataClass -> ViewType.RADIOBUTTON
        }

    fun update(items: List<RateCell>) {
        this.items = items
        this.notifyDataSetChanged()
    }

    private fun getItem(position: Int) = items[position]

    override fun getItemViewType(position: Int): Int = getItem(position).viewType.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsViewHolder {
        return when (viewTypeValues[viewType]) {
            ViewType.RATE -> RateHolder(inflater, parent)
            ViewType.FEEDBACK -> FeedbackHolder(inflater, parent)
            ViewType.BUTTON -> ButtonHolder(inflater, parent)
            ViewType.RADIOBUTTON -> ButtonHolder(inflater, parent)
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
        RADIOBUTTON,
    }
}