package com.example.testtaskappintheair.adapter.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import com.example.testtaskappintheair.R
import com.example.testtaskappintheair.model.RecyclerViewCell

class CheckBoxHolder(
    layoutInflater: LayoutInflater,
    parent: ViewGroup,
    onClickListener: View.OnClickListener
) : AbsViewHolder(layoutInflater.inflate(R.layout.recycler_item_check_box, parent, false)) {

    private val checkBox: CheckBox = itemView.findViewById(R.id.submit_fragment_recycler_feedback_check_box)

    init {
        checkBox.setOnClickListener(onClickListener)
    }

    override fun bind(cell: RecyclerViewCell) {
        val radioButtonCell = cell as RecyclerViewCell.MRadioButtonDataClass
        checkBox.text = radioButtonCell.title
        checkBox.isChecked = radioButtonCell.checked
    }
}