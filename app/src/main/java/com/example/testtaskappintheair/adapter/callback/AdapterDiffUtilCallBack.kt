package com.example.testtaskappintheair.adapter.callback

import androidx.recyclerview.widget.DiffUtil
import com.example.testtaskappintheair.model.RecyclerViewCell

class AdapterDiffUtilCallBack : DiffUtil.ItemCallback<RecyclerViewCell>() {

    override fun areItemsTheSame(oldItem: RecyclerViewCell, newItem: RecyclerViewCell): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: RecyclerViewCell, newItem: RecyclerViewCell): Boolean {
        return oldItem == newItem
    }

}