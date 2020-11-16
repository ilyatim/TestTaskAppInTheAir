package com.example.testtaskappintheair.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.google.android.material.appbar.CollapsingToolbarLayout
import org.w3c.dom.Text

class SubmitFragment : Fragment() {

    companion object {
        fun newInstance() = SubmitFragment()
    }
    private val onSubmitButtonClickListener = View.OnClickListener {
        Toast.makeText(context, "SomeText", Toast.LENGTH_SHORT).show()
    }
    private val onCheckBoxChangeListener = View.OnClickListener {
        Toast.makeText(context, "checkbox is working", Toast.LENGTH_SHORT).show()
    }
    private val onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener {
        ratingBar, rating, fromUser -> Toast.makeText(context, "rating changer is working", Toast.LENGTH_SHORT).show()
    }
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
        override fun afterTextChanged(p0: Editable?) {
            Toast.makeText(context, "edit text is working", Toast.LENGTH_SHORT).show()
        }
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

        activity.setSupportActionBar(view.findViewById<Toolbar>(R.id.submit_fragment_toolbar))
        /*activity.supportActionBar?.apply{
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_clear_24px)
        }*/

        view.findViewById<CollapsingToolbarLayout>(R.id.submit_fragment_collapsing_toolbar_layout).title = " "

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