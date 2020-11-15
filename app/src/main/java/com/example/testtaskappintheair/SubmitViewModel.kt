package com.example.testtaskappintheair

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.testtaskappintheair.model.RecyclerViewCell

class SubmitViewModel(app: Application) : AndroidViewModel(app) {

    private val resources = getApplication<Application>().applicationContext.resources
    private val items: MutableList<RecyclerViewCell> = mutableListOf()

    fun getData(): List<RecyclerViewCell> {
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
                        resources.getString(R.string.feedback_edit_text_title),
                        null,
                        resources.getString(R.string.feedback_edit_text_hint)
                    ),
                    createRateCell(resources.getString(R.string.button_submit_text))
                )
            )
        }
        return items
    }
    private fun createRateCell(title: String, rate: Int):
            RecyclerViewCell.ClassicRateDataClass = RecyclerViewCell.ClassicRateDataClass(title, rate)

    private fun createRateCell(title: String, text: String?, hint: String):
            RecyclerViewCell.FeedbackDataClass = RecyclerViewCell.FeedbackDataClass(title, text, hint)

    private fun createRateCell(title: String):
            RecyclerViewCell.MButtonDataClass = RecyclerViewCell.MButtonDataClass(title)

    private fun createRateCell(title: String, checked: Boolean):
            RecyclerViewCell.MRadioButtonDataClass = RecyclerViewCell.MRadioButtonDataClass(title, checked)
}