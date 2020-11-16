package com.example.testtaskappintheair.model

sealed class RecyclerViewCell {

    data class ClassicRateDataClass(
        val title: String,
        val rate: Int,
        val rateDataType: RateDataType
    ) : RecyclerViewCell()

    data class FeedbackDataClass(
        val title: String,
        val text: String?,
        val hint: String
    ) : RecyclerViewCell()

    data class ButtonDataClass(val title: String) : RecyclerViewCell()

    data class RateWithCheckBoxDataClass(
        val title: String,
        val subtitle: String,
        val rate: Int,
        val checked: Boolean
    ) : RecyclerViewCell()

    enum class RateDataType {
        EXP,
        CROWDED,
        AIRCRAFT,
        SEATS,
        CREW,
    }
}
