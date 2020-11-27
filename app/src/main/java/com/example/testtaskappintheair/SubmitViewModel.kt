package com.example.testtaskappintheair

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testtaskappintheair.model.RecyclerViewCell
import com.example.testtaskappintheair.model.RecyclerViewCell.*
import com.example.testtaskappintheair.model.SubmitObject

class SubmitViewModel(app: Application) : AndroidViewModel(app) {

    private val initRatingValue = 0
    private val initCheckBoxValue = false
    private val expandedHeaderView: MutableLiveData<Boolean> = MutableLiveData(true)
    private val adapterPosition: MutableLiveData<Int> = MutableLiveData()
    private val resources = getApplication<Application>().applicationContext.resources
    private val _submitEvent = SingleLiveEvent<Any>()

    val submitEvent: LiveData<Any>
        get() = _submitEvent

    private val headerRate: MutableLiveData<ClassicRateDataClass> = MutableLiveData(
        createRecyclerCell(
            resources.getString(R.string.header_first_text_view_experience),
            0,
            RateDataType.EXP
        )
    )
    private val _items: MutableLiveData<List<RecyclerViewCell>> =
        MutableLiveData(mutableListOf())
    val items: LiveData<List<RecyclerViewCell>> = _items

    fun getMItems(): LiveData<List<RecyclerViewCell>> = _items

    init {
        initData()
    }

    fun getExpanded(): Boolean = expandedHeaderView.value!!
    fun setExpanded(value: Boolean) = expandedHeaderView.setValue(value)
    fun getAdapterPosition(): Int? = adapterPosition.value
    fun setAdapterPosition(value: Int) {
        adapterPosition.value = value
    }

    fun getData(): List<RecyclerViewCell> {
        if ((_items.value == null) or (_items.value?.isEmpty()!!)) {
            initData()
        }
        return _items.value!!
    }

    /*
    * Func that convert data in Submit object and return it
    * */
    fun getSubmitData(): SubmitObject {
        var exp: Int = this.headerRate.value?.rate!! + 1
        var crowded: Int = 1
        var aircraft: Int = 1
        var seats: Int = 1
        var crew: Int = 1
        var food: Int? = 1
        var feedback: String? = null

        for (item in getData()) {
            when (item) {
                is ClassicRateDataClass -> {
                    when (item.rateDataType) {
                        RateDataType.EXP -> exp = item.rate + 1
                        RateDataType.AIRCRAFT -> aircraft = item.rate + 1
                        RateDataType.SEATS -> seats = item.rate + 1
                        RateDataType.CREW -> crew = item.rate + 1
                    }
                }
                is CrowdRateDataClass -> {
                    crowded = item.rate + 1
                }
                is FeedbackDataClass -> {
                    feedback = if (item.text == "") null else item.text
                }
                is RateWithCheckBoxDataClass -> {
                    food = if (item.checked) null else item.rate + 1
                }
            }
        }
        return SubmitObject(
            exp,
            crowded,
            aircraft,
            seats,
            crew,
            food,
            feedback
        )
    }

    fun dataUpdate(position: Int, newValue: Int): Boolean {
        val item = _items.value?.get(position)
        val newItems: MutableList<RecyclerViewCell> = mutableListOf()
        newItems.addAll(getData())

        return when (item) {
            is ClassicRateDataClass -> {
                newItems.apply {
                    removeAt(position)
                    add(
                        position, createRecyclerCell(
                            item.title,
                            newValue,
                            item.rateDataType
                        )
                    )
                }
                _items.value = newItems
                true
            }
            is CrowdRateDataClass -> {
                newItems.apply {
                    removeAt(position)
                    add(
                        position, createCrowdRateCell(
                            item.title,
                            newValue,
                            item.rateDataType
                        )
                    )
                }
                _items.value = newItems
                true
            }
            else -> false
        }
    }

    fun dataUpdate(
        position: Int,
        rate: Int,
        checked: Boolean
    ): Boolean {

        val item = _items.value?.get(position) as RateWithCheckBoxDataClass
        val newItems: MutableList<RecyclerViewCell> = mutableListOf()
        newItems.addAll(getData())

        newItems.apply {
            removeAt(position)
            add(
                position, createRecyclerCell(
                    item.title,
                    item.subtitle,
                    if (checked) 0 else rate,
                    checked
                )
            )
        }

        _items.value = newItems
        return true
    }

    fun dataUpdate(position: Int, newValue: String): Boolean {

        val item = _items.value?.get(position) as FeedbackDataClass
        val newItems: MutableList<RecyclerViewCell> = mutableListOf()
        newItems.addAll(getData())

        newItems.apply {
            removeAt(position)
            add(
                position, createRecyclerCell(
                    item.title,
                    newValue,
                    item.hint
                )
            )
        }

        _items.value = newItems
        return true
    }

    fun dataUpdate(newRating: Int): Boolean {
        headerRate.value = headerRate.value?.let {
            createRecyclerCell(
                it.title,
                newRating,
                it.rateDataType
            )
        }
        return true
    }

    fun userClickOnSubmitButton() = _submitEvent.call()

    private fun initData() {
        _items.value = listOf(
            createCrowdRateCell(
                resources.getString(R.string.feedback_title_rate_crowded),
                initRatingValue,
                RateDataType.CROWDED
            ),
            createRecyclerCell(
                resources.getString(R.string.feedback_title_rate_aircraft),
                initRatingValue,
                RateDataType.AIRCRAFT
            ),
            createRecyclerCell(
                resources.getString(R.string.feedback_title_rate_seats),
                initRatingValue,
                RateDataType.SEATS
            ),
            createRecyclerCell(
                resources.getString(R.string.feedback_title_rate_crew),
                initRatingValue,
                RateDataType.CREW
            ),
            createRecyclerCell(
                resources.getString(R.string.feedback_title_rate_food),
                resources.getString(R.string.feedback_radio_button_food),
                initRatingValue,
                initCheckBoxValue
            ),
            createRecyclerCell(
                resources.getString(R.string.feedback_edit_text_title),
                null,
                resources.getString(R.string.feedback_edit_text_hint)
            ),
            createRecyclerCell(resources.getString(R.string.button_submit_text))
        )
    }

    private fun createCrowdRateCell(
        title: String,
        rate: Int,
        rateDataType: RateDataType
    ): CrowdRateDataClass = CrowdRateDataClass(
        title,
        rate,
        rateDataType
    )

    private fun createRecyclerCell(
        title: String,
        rate: Int,
        rateDataType: RateDataType
    ): ClassicRateDataClass = ClassicRateDataClass(
        title,
        rate,
        rateDataType
    )

    private fun createRecyclerCell(
        title: String,
        text: String?,
        hint: String
    ): FeedbackDataClass = FeedbackDataClass(
        title,
        text,
        hint
    )

    private fun createRecyclerCell(title: String):
            ButtonDataClass = ButtonDataClass(title)

    private fun createRecyclerCell(
        title: String,
        subtitle: String,
        rating: Int,
        checked: Boolean
    ): RateWithCheckBoxDataClass = RateWithCheckBoxDataClass(
        title,
        subtitle,
        rating,
        checked
    )
}