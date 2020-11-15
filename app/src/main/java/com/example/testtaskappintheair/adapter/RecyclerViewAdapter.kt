package com.example.testtaskappintheair.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskappintheair.adapter.viewHolder.*
import com.example.testtaskappintheair.model.RecyclerViewCell

class RecyclerViewAdapter(
    private var items: List<RecyclerViewCell>,
    private val inflater: LayoutInflater
) : RecyclerView.Adapter<AbsViewHolder>() {

    private lateinit var mListener: MListener
    private val viewTypeValues = ViewType.values()
    private val RecyclerViewCell.viewType: ViewType
        get() = when (this) {
            is RecyclerViewCell.ClassicRateDataClass -> ViewType.RATE
            is RecyclerViewCell.FeedbackDataClass -> ViewType.FEEDBACK
            is RecyclerViewCell.MButtonDataClass -> ViewType.BUTTON
            is RecyclerViewCell.MRadioButtonDataClass -> ViewType.RADIOBUTTON
        }

    fun update(items: List<RecyclerViewCell>) {
        this.items = items
        this.notifyDataSetChanged()
    }

    //TODO: TASK: make it work
    fun setListener(listener: MListener) {
        this.mListener = listener
    }

    private fun getItem(position: Int) = items[position]

    override fun getItemViewType(position: Int): Int = getItem(position).viewType.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsViewHolder {
        return when (viewTypeValues[viewType]) {
            ViewType.RATE -> RateHolder(inflater, parent)
            ViewType.FEEDBACK -> FeedbackHolder(inflater, parent)
            ViewType.BUTTON -> ButtonHolder(inflater, parent)
            ViewType.RADIOBUTTON -> RadioButtonHolder(inflater, parent)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AbsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface MListener {
        fun onClick(view: View)
    }

    private enum class ViewType {
        RATE,
        FEEDBACK,
        BUTTON,
        RADIOBUTTON,
    }
}