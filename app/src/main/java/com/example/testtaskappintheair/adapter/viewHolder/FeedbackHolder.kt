package com.example.testtaskappintheair.adapter.viewHolder

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.example.testtaskappintheair.R
import com.example.testtaskappintheair.adapter.callback.OnTextChangeCallback
import com.example.testtaskappintheair.model.RecyclerViewCell

class FeedbackHolder(
    layoutInflater: LayoutInflater,
    parent: ViewGroup,
    onTextChangeCallback: OnTextChangeCallback
) : AbsViewHolder(
    layoutInflater.inflate(
        R.layout.recycler_item_feedback,
        parent,
        false
    )
) {

    private val titleTextView: TextView =
        itemView.findViewById(R.id.submit_fragment_recycler_feedback_text_view)
    private val editText: EditText =
        itemView.findViewById(R.id.submit_fragment_recycler_feedback_edit_text)

    init {
        editText.addTextChangedListener { text: Editable? ->
            onTextChangeCallback.onTextChange(adapterPosition, text.toString().trim())
        }
    }

    override fun bind(cell: RecyclerViewCell) {
        val feedBackCell = cell as RecyclerViewCell.FeedbackDataClass
        titleTextView.text = feedBackCell.title
        editText.setText((feedBackCell.text ?: ""), TextView.BufferType.EDITABLE)
        editText.hint = feedBackCell.hint
    }
}