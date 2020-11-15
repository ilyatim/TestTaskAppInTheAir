package com.example.testtaskappintheair.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.testtaskappintheair.R
import com.example.testtaskappintheair.model.RecyclerViewCell

class RadioButtonHolder(
    layoutInflater: LayoutInflater,
    parent: ViewGroup
) : AbsViewHolder(layoutInflater.inflate(R.layout.recycler_item_radio_button, parent, false)),
    RadioGroup.OnCheckedChangeListener {

    private val radioButton: RadioButton = itemView.findViewById(R.id.submit_fragment_recycler_feedback_radio_button)

    override fun bind(cell: RecyclerViewCell) {
        val radioButtonCell = cell as RecyclerViewCell.MRadioButtonDataClass
        radioButton.text = radioButtonCell.title
        radioButton.isChecked = radioButtonCell.checked
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        TODO("Not yet implemented")
    }

}