package com.example.testtaskappintheair.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
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
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SubmitFragment : Fragment() {

    companion object {
        fun newInstance() = SubmitFragment()
    }

    private val duration = 350L
    private val viewModel: SubmitViewModel by viewModels()
    private lateinit var adapter: RecyclerViewAdapter

    private val onSubmitButtonClickListener = View.OnClickListener {
        Toast.makeText(
            context,
            viewModel.getSubmitData().toJson(),
            Toast.LENGTH_SHORT
        ).show()
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
            if (checked and (rating > 0)) {
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
    private val onTextChangeListener = object : OnTextChangeCallback {
        private var searchFor = ""
        override fun onTextChange(pos: Int, text: String) {
            val searchText = text.trim()
            if (searchText == searchFor) return
            searchFor = searchText
            lifecycleScope.launch {
                delay(300)
                if (searchText != searchFor) return@launch
                viewModel.dataUpdate(pos, searchText)
            }
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
        val textViewHeaderText = view.findViewById<TextView>(R.id.headerTextViewExperience)
        val textViewHeaderTextInfo = view.findViewById<TextView>(R.id.headerTextViewInfo)
        val mAppBarLayout = view.findViewById<AppBarLayout>(R.id.submit_fragment_app_bar)

        headerRatingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            viewModel.dataUpdate(rating.toInt())
        }

        activity.setSupportActionBar(view.findViewById(R.id.submit_fragment_toolbar))
        view.findViewById<CollapsingToolbarLayout>(R.id.submit_fragment_collapsing_toolbar_layout).title = " "
        view.findViewById<Toolbar>(R.id.submit_fragment_toolbar).setNavigationOnClickListener {
            activity.finish()
        }

        mAppBarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (appBarLayout.totalScrollRange + verticalOffset < 650) {
                    animStart(
                        textViewHeaderText,
                        0f,
                        View.GONE
                    )
                    animStart(
                        textViewHeaderTextInfo,
                        0f,
                        View.GONE
                    )
                    animStart(
                        headerRatingBar,
                        0f,
                        View.GONE
                    )
                } else {
                    animStart(
                        textViewHeaderText,
                        1f,
                        View.VISIBLE
                    )
                    animStart(
                        textViewHeaderTextInfo,
                        1f,
                        View.VISIBLE
                    )
                    animStart(
                        headerRatingBar,
                        1f,
                        View.VISIBLE
                    )
                }
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = RecyclerViewAdapter(
            viewModel.getData(),
            inflater,
            onSubmitButtonClickListener,
            onCheckBoxChangeListener,
            onRatingBarChangeListener,
            onTextChangeListener
        )
        recyclerView.adapter = adapter

        return view
    }

    private fun animStart(
        view: View,
        alpha: Float,
        visibility: Int
    ) {
        view.animate()
            .setDuration(duration)
            .alpha(alpha)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    view.visibility = visibility
                }
            })
    }
}