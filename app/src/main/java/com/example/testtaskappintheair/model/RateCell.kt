package com.example.testtaskappintheair.model

sealed class RateCell {
    data class ClassicRateDataClass(val title: String, val rate: Int) : RateCell()
    data class FeedbackDataClass(val title: String, val text: String?, val hint: String) : RateCell()
    data class MButtonDataClass(val title: String) : RateCell()
    data class MRadioButtonDataClass(val title: String, val checked: Boolean) : RateCell()
}
