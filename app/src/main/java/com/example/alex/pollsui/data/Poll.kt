package com.example.alex.pollsui.data

import com.google.gson.annotations.SerializedName

data class Poll constructor(
        @SerializedName("name") val title: String,
        @SerializedName("author") val author: String? = null,
        @SerializedName("id") val id: String? = null,
        @SerializedName("questions") val questions: MutableList<Question> = ArrayList()
)
