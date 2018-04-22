package com.example.alex.pollsui.data

import android.support.annotation.NonNull
import java.util.*

data class Answer constructor(@NonNull val title: String) {
    val id: String = UUID.randomUUID().toString()
}
