package com.example.alex.pollsui.data

import java.util.*
import kotlin.collections.ArrayList

data class Poll constructor(val title: String, val author: User) {
    val id: String = UUID.randomUUID().toString()
    val questions: List<Question> = ArrayList()
}
