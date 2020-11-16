package com.example.testtaskappintheair.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskappintheair.R
import com.example.testtaskappintheair.SubmitViewModel
import com.example.testtaskappintheair.adapter.RecyclerViewAdapter
import com.example.testtaskappintheair.adapter.callback.OnCheckBoxChangeListenerCallback
import com.example.testtaskappintheair.adapter.callback.OnRatingBarChangeListenerCallback
import com.google.android.material.appbar.CollapsingToolbarLayout
import org.w3c.dom.Text

class SubmitFragment : Fragment() {

    companion object {
        fun newInstance() = SubmitFragment()
    }

    private val viewModel: SubmitViewModel by viewModels()
    private val onSubmitButtonClickListener = View.OnClickListener {
        Toast.makeText(context, viewModel.getSubmitData().toJson(), Toast.LENGTH_SHORT).show()
    }
    private val onCheckBoxChangeListener = object : OnCheckBoxChangeListenerCallback {
        override fun onStateChange(
            pos: Int,
            rating: Int,
            foodExist: Boolean,
            ratingBar: RatingBar,
            checkBox: CheckBox
        ) {
            //TODO: checkBox processing for UI
            viewModel.dataUpdate(pos, rating, foodExist)
        }
    }
    private val onRatingBarChangeListener = object : OnRatingBarChangeListenerCallback {
        override fun onRatingBarChange(
            pos: Int,
            ratingBar: RatingBar,
            rating: Float,
            fromUser: Boolean
        ) {
            viewModel.dataUpdate(pos, rating.toInt())
        }
    }
    private val textWatcher = object : TextWatcher {
        //TODO: text changing processing
        override fun beforeTextChanged(
            p0: CharSequence?,
            p1: Int,
            p2: Int,
            p3: Int
        ) {

        }
        override fun onTextChanged(
            p0: CharSequence?,
            p1: Int,
            p2: Int,
            p3: Int
        ) {

        }
        override fun afterTextChanged(p0: Editable?) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(
            R.layout.submit_fragment,
            container,
            false
        )
        val activity = activity as AppCompatActivity
        val recyclerView: RecyclerView = view.findViewById(R.id.submit_fragment_recycler_view)

        activity.setSupportActionBar(view.findViewById(R.id.submit_fragment_toolbar))
        view.findViewById<CollapsingToolbarLayout>(R.id.submit_fragment_collapsing_toolbar_layout).title = " "
        view.findViewById<Toolbar>(R.id.submit_fragment_toolbar).setNavigationOnClickListener {
            activity.finish()
        }

        //TODO viewModel.headerRate processing
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = RecyclerViewAdapter(
            viewModel.getData(),
            inflater,
            onSubmitButtonClickListener,
            onCheckBoxChangeListener,
            onRatingBarChangeListener,
            textWatcher
        )

        return view
    }
}