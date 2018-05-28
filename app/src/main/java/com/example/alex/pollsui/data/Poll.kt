package com.example.alex.pollsui.data

import com.google.gson.annotations.SerializedName

data class Poll constructor(
        @SerializedName("name") val title: String,
        @SerializedName("author") val author: String? = null,
        @SerializedName("id") val id: String? = null,
        @SerializedName("questions") val questions: MutableList<Question> = ArrayList()
) {
    fun getStatisticString(): String {
        val sb = StringBuilder()
        for (i in questions.indices) {
            val question = questions[i]
            sb.append("${i + 1}) ${question.title}").append("\n")
            for (j in question.answers.indices) {
                val answer = question.answers[j]
                sb.append("\t${j + 1}. ${answer.title} : ${answer.count}").append("\n")
            }
            sb.append("\n")
        }
        return sb.toString()
    }
}
