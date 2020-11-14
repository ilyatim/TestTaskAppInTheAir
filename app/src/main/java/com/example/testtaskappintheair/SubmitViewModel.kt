package com.example.testtaskappintheair

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.testtaskappintheair.model.RateCell

class SubmitViewModel(app: Application) : AndroidViewModel(app) {

    private val resources = getApplication<Application>().applicationContext.resources
    private val items: MutableList<RateCell> = mutableListOf()

    fun getData(): List<RateCell> {
        if (items.isEmpty()) {
            items.addAll(
                listOf(
                    createRateCell(resources.getString(R.string.feedback_title_rate_crowded), 0),
                    createRateCell(resources.getString(R.string.feedback_title_rate_aircraft), 0),
                    createRateCell(resources.getString(R.string.feedback_title_rate_seats), 0),
                    createRateCell(resources.getString(R.string.feedback_title_rate_crew), 0),
                    createRateCell(resources.getString(R.string.feedback_title_rate_food), 0),
                    createRateCell(resources.getString(R.string.feedback_radio_button_food), false),
                    createRateCell(
                        resources.getString(R.string.feedback_title_rate_crowded),
                        "",
                        resources.getString(R.string.feedback_edit_text_hint)
                    ),
                    createRateCell(resources.getString(R.string.feedback_title_rate_crowded))
                )
            )
        }
        return items
    }
    private fun createRateCell(title: String, rate: Int):
            RateCell.ClassicRateDataClass = RateCell.ClassicRateDataClass(title, rate)

    private fun createRateCell(title: String, text: String, hint: String):
            RateCell.FeedbackDataClass = RateCell.FeedbackDataClass(title, text, hint)

    private fun createRateCell(title: String):
            RateCell.MButtonDataClass = RateCell.MButtonDataClass(title)

    private fun createRateCell(title: String, checked: Boolean):
            RateCell.MRadioButtonDataClass = RateCell.MRadioButtonDataClass(title, checked)
}