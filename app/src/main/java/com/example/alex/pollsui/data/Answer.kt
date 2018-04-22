package com.example.alex.pollsui.data

import java.util.*

data class Answer constructor(val title: String) {
    val id: String = UUID.randomUUID().toString()
}
