package com.example.alex.pollsui.data

import java.util.*
import kotlin.collections.ArrayList

data class Question constructor(val title: String) {
    val id: String = UUID.randomUUID().toString()
    var selectedAnswer: String? = null
    val answers: List<String> = ArrayList()
}
