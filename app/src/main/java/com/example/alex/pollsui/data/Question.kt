package com.example.alex.pollsui.data

import com.google.gson.annotations.SerializedName

data class Question constructor(
        @SerializedName("text") val title: String,
        @SerializedName("id") val id: String? = null,
        @SerializedName("answers") val answers: MutableList<Answer> = ArrayList(),
        var selectedAnswer: String? = null
)
