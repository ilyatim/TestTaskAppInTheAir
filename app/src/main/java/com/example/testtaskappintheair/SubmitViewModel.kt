package com.example.testtaskappintheair

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.testtaskappintheair.model.RecyclerViewCell.*
import com.example.testtaskappintheair.model.RecyclerViewCell
import com.example.testtaskappintheair.model.RecyclerViewCell.ClassicRateDataClass
import com.example.testtaskappintheair.model.SubmitObject

class SubmitViewModel(app: Application) : AndroidViewModel(app) {

    private val resources = getApplication<Application>().applicationContext.resources

    private val headerRate: MutableLiveData<ClassicRateDataClass> = MutableLiveData(createRecyclerCell(
        resources.getString(R.string.header_first_text_view_experience),
        0,
        RateDataType.EXP
    ))
    private val items: MutableLiveData<MutableList<RecyclerViewCell>>
            = MutableLiveData(mutableListOf())

    fun getData(): List<RecyclerViewCell> {
        if ((items.value == null) or (items.value?.isEmpty()!!)) {
            initData()
        }
        return items.value!!
    }
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
                        RateDataType.CROWDED -> crowded = item.rate + 1
                        RateDataType.AIRCRAFT -> aircraft = item.rate + 1
                        RateDataType.SEATS -> seats = item.rate + 1
                        RateDataType.CREW -> crew = item.rate + 1
                    }
                }
                is FeedbackDataClass -> {
                    feedback = item.text
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
        val item = items.value?.removeAt(position) as ClassicRateDataClass
        items.value?.apply {
            add(position, createRecyclerCell(item.title, newValue, item.rateDataType))
        }
        return true
    }
    fun dataUpdate(position: Int, rate: Int, checked: Boolean): Boolean {
        val item = items.value?.removeAt(position) as RateWithCheckBoxDataClass
        items.value?.apply {
            add(position, createRecyclerCell(
                item.title,
                item.subtitle,
                if (checked) 0 else rate,
                checked
            ))
        }
        return true
    }
    fun dataUpdate(position: Int, newValue: String): Boolean{
        val item = items.value?.removeAt(position) as FeedbackDataClass
        items.value?.apply {
            add(position, createRecyclerCell(item.title, newValue, item.hint))
        }
        return true
    }
    fun dataUpdate(newRating: Int): Boolean {
        headerRate.value = headerRate.value?.let { createRecyclerCell(it.title, newRating, it.rateDataType) }
        return true
    }

    private fun initData() {
        items.value = mutableListOf()
        items.value?.addAll(
            listOf(
                createRecyclerCell(
                    resources.getString(R.string.feedback_title_rate_crowded),
                    0,
                    RateDataType.CROWDED
                ),
                createRecyclerCell(
                    resources.getString(R.string.feedback_title_rate_aircraft),
                    0,
                    RateDataType.AIRCRAFT
                ),
                createRecyclerCell(
                    resources.getString(R.string.feedback_title_rate_seats),
                    0,
                    RateDataType.SEATS
                ),
                createRecyclerCell(
                    resources.getString(R.string.feedback_title_rate_crew),
                    0,
                    RateDataType.CREW
                ),
                createRecyclerCell(
                    resources.getString(R.string.feedback_title_rate_food),
                    resources.getString(R.string.feedback_radio_button_food),
                    0,
                    false
                ),
                createRecyclerCell(
                    resources.getString(R.string.feedback_edit_text_title),
                    null,
                    resources.getString(R.string.feedback_edit_text_hint)
                ),
                createRecyclerCell(resources.getString(R.string.button_submit_text))
            )
        )
    }
    private fun createRecyclerCell(title: String, rate: Int, rateDataType: RateDataType):
            ClassicRateDataClass = ClassicRateDataClass(title, rate, rateDataType)

    private fun createRecyclerCell(title: String, text: String?, hint: String):
            FeedbackDataClass = FeedbackDataClass(title, text, hint)

    private fun createRecyclerCell(title: String):
            ButtonDataClass = ButtonDataClass(title)

    private fun createRecyclerCell(title: String, subtitle: String, rating: Int, checked: Boolean):
            RateWithCheckBoxDataClass = RateWithCheckBoxDataClass(title, subtitle, rating, checked)
}