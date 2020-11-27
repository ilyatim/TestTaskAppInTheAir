package com.example.testtaskappintheair.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskappintheair.R
import com.example.testtaskappintheair.SubmitViewModel
import com.example.testtaskappintheair.adapter.RecyclerViewAdapter
import com.example.testtaskappintheair.adapter.callback.AdapterDiffUtilCallBack
import com.example.testtaskappintheair.adapter.callback.OnCheckBoxChangeCallback
import com.example.testtaskappintheair.adapter.callback.OnRatingBarChangeCallback
import com.example.testtaskappintheair.adapter.callback.OnTextChangeCallback
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.submit_fragment.*
import kotlinx.android.synthetic.main.submit_fragment_content.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SubmitFragment : Fragment() {

    companion object {
        fun newInstance() = SubmitFragment()
    }

    private val durationOfHidingText = 350L
    private val durationOfTextDisplayAnimation = 40L
    private val progressBarShowDelay = 1000L
    private val alphaVisible = 1f
    private val alphaInvisible = 0f
    private val viewModel: SubmitViewModel by viewModels()
    private var adapter: RecyclerViewAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var collapsingToolbarLayout: CollapsingToolbarLayout? = null
    private var mAppBarLayout: AppBarLayout? = null

    private val onSubmitButtonClickListener = View.OnClickListener {
        viewModel.userClickOnSubmitButton()
    }
    private val onCheckBoxChangeListener = object : OnCheckBoxChangeCallback {
        override fun onCheckBoxStateChange(
            pos: Int,
            checked: Boolean,
            rating: Int,
            ratingBar: RatingBar
        ) {
            viewModel.dataUpdate(
                pos,
                rating,
                checked
            )
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

        viewModel.items.observe(viewLifecycleOwner, Observer {
            adapter?.submitList(it)
        })

        viewModel.submitEvent.observe(viewLifecycleOwner, Observer {
            submit_fragment_progress_bar_layout.visibility = View.VISIBLE
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            lifecycleScope.launch {
                delay(progressBarShowDelay)
                submit_fragment_progress_bar_layout.visibility = View.GONE
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                Toast.makeText(
                    context,
                    "Task success ${viewModel.getSubmitData().toJson()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        mAppBarLayout?.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (verticalOffset < 0) {
                    animInit(
                        textViewHeaderText,
                        alphaInvisible,
                        View.GONE,
                        durationOfHidingText
                    )
                    animInit(
                        textViewHeaderTextInfo,
                        alphaInvisible,
                        View.GONE,
                        durationOfHidingText
                    )
                    animInit(
                        headerRatingBar,
                        alphaInvisible,
                        View.GONE,
                        durationOfHidingText
                    )
                    viewModel.setExpanded(false)
                } else {
                    animInit(
                        textViewHeaderText,
                        alphaVisible,
                        View.VISIBLE,
                        durationOfTextDisplayAnimation
                    )
                    animInit(
                        textViewHeaderTextInfo,
                        alphaVisible,
                        View.VISIBLE,
                        durationOfTextDisplayAnimation
                    )
                    animInit(
                        headerRatingBar,
                        alphaVisible,
                        View.VISIBLE,
                        durationOfTextDisplayAnimation
                    )
                    viewModel.setExpanded(true)
                }
            }
        )


        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView?.layoutManager = linearLayoutManager
        adapter = RecyclerViewAdapter(
            inflater,
            onSubmitButtonClickListener,
            onCheckBoxChangeListener,
            onRatingBarChangeListener,
            onTextChangeListener,
            AdapterDiffUtilCallBack()
        )
        recyclerView?.adapter = adapter
        recyclerView?.setHasFixedSize(true)
        recyclerView?.itemAnimator = null
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
        visibility: Int,
        duration: Long
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