package com.example.testtaskappintheair.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.testtaskappintheair.adapter.callback.AdapterDiffUtilCallBack
import com.example.testtaskappintheair.adapter.callback.OnCheckBoxChangeCallback
import com.example.testtaskappintheair.adapter.callback.OnRatingBarChangeCallback
import com.example.testtaskappintheair.adapter.callback.OnTextChangeCallback
import com.example.testtaskappintheair.adapter.viewHolder.*
import com.example.testtaskappintheair.model.RecyclerViewCell

class RecyclerViewAdapter(
    private val inflater: LayoutInflater,
    private val buttonClickListener: View.OnClickListener,
    private val checkBoxClickListener: OnCheckBoxChangeCallback,
    private val onRatingBarChangeListener: OnRatingBarChangeCallback,
    private val onTextChangeCallback: OnTextChangeCallback,
    private val diffUtil: AdapterDiffUtilCallBack
) : ListAdapter<RecyclerViewCell, AbsViewHolder>(diffUtil) {

    private val viewTypeValues = ViewType.values()
    private val RecyclerViewCell.viewType: ViewType
        get() = when (this) {
            is RecyclerViewCell.ClassicRateDataClass -> ViewType.CLASSICRATE
            is RecyclerViewCell.CrowdRateDataClass -> ViewType.CROWDRATE
            is RecyclerViewCell.FeedbackDataClass -> ViewType.FEEDBACK
            is RecyclerViewCell.ButtonDataClass -> ViewType.BUTTON
            is RecyclerViewCell.RateWithCheckBoxDataClass -> ViewType.CHECKBOX
        }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsViewHolder {
        return when (viewTypeValues[viewType]) {
            ViewType.CLASSICRATE -> ClassicRateHolder(
                inflater,
                parent,
                onRatingBarChangeListener
            )
            ViewType.CROWDRATE -> CrowdRateHolder(
                inflater,
                parent,
                onRatingBarChangeListener
            )
            ViewType.FEEDBACK -> FeedbackHolder(
                inflater,
                parent,
                onTextChangeCallback
            )
            ViewType.BUTTON -> ButtonHolder(
                inflater,
                parent,
                buttonClickListener
            )
            ViewType.CHECKBOX -> CheckBoxHolder(
                inflater,
                parent,
                checkBoxClickListener
            )
        }
    }

    override fun onBindViewHolder(holder: AbsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private enum class ViewType {
        CLASSICRATE,
        CROWDRATE,
        FEEDBACK,
        BUTTON,
        CHECKBOX,
    }
}