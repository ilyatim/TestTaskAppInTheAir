package com.example.testtaskappintheair.adapter.viewHolder

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.testtaskappintheair.R
import com.example.testtaskappintheair.model.RateCell

class FeedbackHolder(layoutInflater: LayoutInflater, parent: ViewGroup) :
    AbsViewHolder(layoutInflater.inflate(R.layout.recycler_item_feedback, parent, false)) {

    private val titleTextView: TextView
    private val editText: EditText

    init {
        val view = layoutInflater.inflate(R.layout.recycler_item_feedback, parent, false)
        titleTextView = view.findViewById(R.id.textViewFeedback)
        editText = view.findViewById(R.id.editTextTextMultiLine)
    }
    override fun bind(cell: RateCell) {
        val rateCell = cell as RateCell.FeedbackDataClassModel
        titleTextView.text = rateCell.feedbackItem.title
        editText.hint = rateCell.feedbackItem.text
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

}