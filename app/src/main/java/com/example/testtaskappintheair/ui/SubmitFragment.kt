package com.example.testtaskappintheair.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskappintheair.R
import com.example.testtaskappintheair.SubmitViewModel
import com.example.testtaskappintheair.adapter.RecyclerViewAdapter
import com.example.testtaskappintheair.model.RateCell
import com.example.testtaskappintheair.model.data.FeedbackDataClass
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.submit_fragment_content.*

class SubmitFragment : Fragment() {

    companion object {
        fun newInstance() = SubmitFragment()
    }

    private val someList: List<RateCell> = listOf(RateCell.FeedbackDataClassModel(FeedbackDataClass("some title", "input here your text")))
    private lateinit var viewModel: SubmitViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.submit_fragment, container, false)
        val activity = activity as AppCompatActivity
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        activity.setSupportActionBar(view.findViewById(R.id.toolbar))
        view.findViewById<CollapsingToolbarLayout>(R.id.submit_fragment_collapsing_toolbar_layout).title = activity.title

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = RecyclerViewAdapter(someList, inflater)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SubmitViewModel::class.java)
        // TODO: Use the ViewModel
    }

}