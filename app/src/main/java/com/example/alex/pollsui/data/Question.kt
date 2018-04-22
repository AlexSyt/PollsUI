package com.example.alex.pollsui.data

import android.support.annotation.NonNull
import java.util.*
import kotlin.collections.ArrayList

data class Question constructor(@NonNull val title: String) {
    val id: String = UUID.randomUUID().toString()
    var selectedAnswer: String? = null
    val answers: List<String> = ArrayList()
}
