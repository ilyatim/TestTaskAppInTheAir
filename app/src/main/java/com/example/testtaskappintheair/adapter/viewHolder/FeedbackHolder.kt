package com.example.testtaskappintheair.adapter.viewHolder

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.testtaskappintheair.R
import com.example.testtaskappintheair.model.RateCell

class FeedbackHolder(
    layoutInflater: LayoutInflater,
    parent: ViewGroup
) : AbsViewHolder(layoutInflater.inflate(R.layout.recycler_item_feedback, parent, false)) {

    private val titleTextView: TextView = itemView.findViewById(R.id.submit_fragment_recycler_feedback_text_view)
    private val editText: EditText = itemView.findViewById(R.id.submit_fragment_recycler_feedback_edit_text)

    override fun bind(cell: RateCell) {
        val feedBackCell = cell as RateCell.FeedbackDataClass
        titleTextView.text = feedBackCell.title
        editText.text = (feedBackCell.text ?: "") as Editable?
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

}