package com.example.testtaskappintheair.adapter

import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskappintheair.adapter.callback.OnCheckBoxChangeCallback
import com.example.testtaskappintheair.adapter.callback.OnRatingBarChangeCallback
import com.example.testtaskappintheair.adapter.callback.OnTextChangeCallback
import com.example.testtaskappintheair.adapter.viewHolder.*
import com.example.testtaskappintheair.model.RecyclerViewCell

class RecyclerViewAdapter(
    private var items: List<RecyclerViewCell>,
    private val inflater: LayoutInflater,
    private val buttonClickListener: View.OnClickListener,
    private val checkBoxClickListener: OnCheckBoxChangeCallback,
    private val onRatingBarChangeListener: OnRatingBarChangeCallback,
    private val textWatcher: TextWatcher,
    private val onTextChangeCallback: OnTextChangeCallback
) : RecyclerView.Adapter<AbsViewHolder>() {

    private val viewTypeValues = ViewType.values()
    private val RecyclerViewCell.viewType: ViewType
        get() = when (this) {
            is RecyclerViewCell.ClassicRateDataClass -> ViewType.RATE
            is RecyclerViewCell.FeedbackDataClass -> ViewType.FEEDBACK
            is RecyclerViewCell.ButtonDataClass -> ViewType.BUTTON
            is RecyclerViewCell.RateWithCheckBoxDataClass -> ViewType.RADIOBUTTON
        }

    fun updateAll(newItems: List<RecyclerViewCell>, pos: Int) {
        items = newItems
        notifyItemChanged(pos)
    }

    private fun getItem(position: Int) = items[position]

    override fun getItemViewType(position: Int): Int = getItem(position).viewType.ordinal
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsViewHolder {
        return when (viewTypeValues[viewType]) {
            ViewType.RATE -> RateHolder(
                inflater,
                parent,
                onRatingBarChangeListener
            )
            ViewType.FEEDBACK -> FeedbackHolder(
                inflater,
                parent,
                textWatcher,
                onTextChangeCallback
            )
            ViewType.BUTTON -> ButtonHolder(
                inflater,
                parent,
                buttonClickListener
            )
            ViewType.RADIOBUTTON -> CheckBoxHolder(
                inflater,
                parent,
                checkBoxClickListener
            )
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