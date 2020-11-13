package com.example.testtaskappintheair.model

import com.example.testtaskappintheair.model.data.ClassicRateDataClass
import com.example.testtaskappintheair.model.data.FeedbackDataClass
import com.example.testtaskappintheair.model.data.MButtonDataClass

sealed class RateCell {
    class RateDataClassModel(val classicRateItem: ClassicRateDataClass) : RateCell()
    class FeedbackDataClassModel(val feedbackItem: FeedbackDataClass) : RateCell()
    class ButtonDataClassModel(val buttonItem: MButtonDataClass) : RateCell()
}
