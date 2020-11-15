package com.example.testtaskappintheair.model

sealed class RecyclerViewCell {
    data class ClassicRateDataClass(val title: String, val rate: Int) : RecyclerViewCell()
    data class FeedbackDataClass(val title: String, val text: String?, val hint: String) : RecyclerViewCell()
    data class MButtonDataClass(val title: String) : RecyclerViewCell()
    data class MRadioButtonDataClass(val title: String, val checked: Boolean) : RecyclerViewCell()
}
