package com.example.alex.pollsui.data

import android.support.annotation.NonNull
import java.util.*
import kotlin.collections.ArrayList

data class Poll constructor(@NonNull val title: String, @NonNull val author: User) {
    val id: String = UUID.randomUUID().toString()
    val questions: List<Question> = ArrayList()
}
