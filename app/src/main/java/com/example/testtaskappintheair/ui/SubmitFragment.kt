package com.example.testtaskappintheair.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskappintheair.R
import com.example.testtaskappintheair.SubmitViewModel
import com.example.testtaskappintheair.adapter.RecyclerViewAdapter
import com.example.testtaskappintheair.model.RateCell
import com.google.android.material.appbar.CollapsingToolbarLayout

class SubmitFragment : Fragment() {

    companion object {
        fun newInstance() = SubmitFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel: SubmitViewModel by viewModels()
        val view = inflater.inflate(R.layout.submit_fragment, container, false)
        val activity = activity as AppCompatActivity
        val recyclerView: RecyclerView = view.findViewById(R.id.submit_fragment_recycler_view)

        activity.setSupportActionBar(view.findViewById(R.id.submit_fragment_toolbar))
        view.findViewById<CollapsingToolbarLayout>(R.id.submit_fragment_collapsing_toolbar_layout).title = activity.title

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = RecyclerViewAdapter(viewModel.getData(), inflater)

        return view
    }
}