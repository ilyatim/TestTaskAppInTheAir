package com.example.testtaskappintheair.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskappintheair.R
import com.example.testtaskappintheair.SubmitViewModel
import com.example.testtaskappintheair.adapter.RecyclerViewAdapter
import com.example.testtaskappintheair.adapter.callback.OnCheckBoxChangeCallback
import com.example.testtaskappintheair.adapter.callback.OnRatingBarChangeCallback
import com.example.testtaskappintheair.adapter.callback.OnTextChangeCallback
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubmitFragment : Fragment() {

    companion object {
        fun newInstance() = SubmitFragment()
    }

    private val viewModel: SubmitViewModel by viewModels()
    private lateinit var adapter: RecyclerViewAdapter

    private val onSubmitButtonClickListener = View.OnClickListener {
        Toast.makeText(context, viewModel.getSubmitData().toJson(), Toast.LENGTH_SHORT).show()
    }
    private val onCheckBoxChangeListener = object : OnCheckBoxChangeCallback {
        override fun onCheckBoxStateChange(
            pos: Int,
            checked: Boolean,
            rating: Int,
            ratingBar: RatingBar
        ) {
            ratingBar.setIsIndicator(checked)
            viewModel.dataUpdate(
                pos,
                rating,
                checked
            )
            if (checked) {
                lifecycleScope.launch(Dispatchers.Main) {
                    adapter.updateAll(viewModel.getData(), pos)
                }
            }
        }
        override fun onRatingBarChange(
            pos: Int,
            rating: Int,
            foodExist: Boolean
        ) {
            viewModel.dataUpdate(
                pos,
                rating,
                foodExist
            )
        }
    }
    private val onRatingBarChangeListener = object : OnRatingBarChangeCallback {
        override fun onRatingBarChange(
            pos: Int,
            rating: Float
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
    //TODO: refactor
    private val onTextChangeListener = object : OnTextChangeCallback {
        override fun onTextChange(pos: Int, text: String) {
            viewModel.dataUpdate(pos, text)
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
        val headerRatingBar = view.findViewById<RatingBar>(R.id.header_rating_bar)
        headerRatingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            viewModel.dataUpdate(rating.toInt())
        }

        activity.setSupportActionBar(view.findViewById(R.id.submit_fragment_toolbar))
        view.findViewById<CollapsingToolbarLayout>(R.id.submit_fragment_collapsing_toolbar_layout).title = " "
        view.findViewById<Toolbar>(R.id.submit_fragment_toolbar).setNavigationOnClickListener {
            activity.finish()
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = RecyclerViewAdapter(
            viewModel.getData(),
            inflater,
            onSubmitButtonClickListener,
            onCheckBoxChangeListener,
            onRatingBarChangeListener,
            textWatcher,
            onTextChangeListener
        )
        recyclerView.adapter = adapter

        return view
    }
}