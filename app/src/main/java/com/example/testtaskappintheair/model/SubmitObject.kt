package com.example.testtaskappintheair.model

data class SubmitObject(
    private val experience: Int,
    private val crowded: Int,
    private val aircraft: Int,
    private val seats: Int,
    private val crew: Int,
    private val food: Int?,
    private val feedback: String?
) {
    fun toJson(): String {
        return """
            |{
            |   "experience":$experience,
            |   "crowded":$crowded,
            |   "aircraft":$aircraft,
            |   "seats":$seats,
            |   "crew":$crew,
            |   "food":$food,
            |   "feedback":$feedback
            |}
        """.trimMargin()
    }
}