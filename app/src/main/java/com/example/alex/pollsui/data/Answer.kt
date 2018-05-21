package com.example.alex.pollsui.data

import com.google.gson.annotations.SerializedName

data class Answer constructor(
        @SerializedName("text") val title: String,
        @SerializedName("id") val id: String? = null,
        @SerializedName("count") val count: Int = 0
)
