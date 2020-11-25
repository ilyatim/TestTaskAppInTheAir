package com.example.testtaskappintheair.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
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
import kotlinx.android.synthetic.main.submit_fragment.*
import kotlinx.android.synthetic.main.submit_fragment_content.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SubmitFragment : Fragment() {

    companion object {
        fun newInstance() = SubmitFragment()
    }

    private val duration = 350L
    private val alphaVisible = 1f
    private val alphaInvisible = 0f
    private val viewModel: SubmitViewModel by viewModels()
    private var adapter: RecyclerViewAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var collapsingToolbarLayout: CollapsingToolbarLayout? = null
    private var mAppBarLayout: AppBarLayout? = null

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
                    adapter?.updateAll(viewModel.getData(), pos)
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

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        mAppBarLayout?.setExpanded(viewModel.getExpanded())
        recyclerView?.layoutManager?.scrollToPosition(viewModel.getAdapterPosition() ?: 0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
        viewModel.setAdapterPosition(layoutManager.findLastVisibleItemPosition())
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
        val headerRatingBar = view.findViewById<RatingBar>(R.id.header_rating_bar)
        val textViewHeaderText = view.findViewById<TextView>(R.id.headerTextViewExperience)
        val textViewHeaderTextInfo = view.findViewById<TextView>(R.id.headerTextViewInfo)

        mAppBarLayout = view.findViewById(R.id.submit_fragment_app_bar)
        collapsingToolbarLayout = view.findViewById(R.id.submit_fragment_collapsing_toolbar_layout)
        recyclerView = view.findViewById(R.id.submit_fragment_recycler_view)

        headerRatingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            viewModel.dataUpdate(rating.toInt())
        }

        collapsingToolbarLayout?.title = " "
        view.findViewById<Toolbar>(R.id.submit_fragment_toolbar).setNavigationOnClickListener {
            activity.finish()
        }

        mAppBarLayout?.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (appBarLayout.totalScrollRange + verticalOffset < 650) {
                    animInit(
                        textViewHeaderText,
                        alphaInvisible,
                        View.GONE
                    )
                    animInit(
                        textViewHeaderTextInfo,
                        alphaInvisible,
                        View.GONE
                    )
                    animInit(
                        headerRatingBar,
                        alphaInvisible,
                        View.GONE
                    )
                    viewModel.setExpanded(false)
                } else {
                    animInit(
                        textViewHeaderText,
                        alphaVisible,
                        View.VISIBLE
                    )
                    animInit(
                        textViewHeaderTextInfo,
                        alphaVisible,
                        View.VISIBLE
                    )
                    animInit(
                        headerRatingBar,
                        alphaVisible,
                        View.VISIBLE
                    )
                    viewModel.setExpanded(true)
                }
            }
        )
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView?.layoutManager = linearLayoutManager
        adapter = RecyclerViewAdapter(
            viewModel.getData(),
            inflater,
            onSubmitButtonClickListener,
            onCheckBoxChangeListener,
            onRatingBarChangeListener,
            onTextChangeListener
        )
        recyclerView?.adapter = adapter
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView = null
        mAppBarLayout = null
        collapsingToolbarLayout = null
        adapter = null
    }

    /**
     * Animate alpha of view
     */
    private fun animInit(
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