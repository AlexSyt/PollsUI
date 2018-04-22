package com.example.alex.pollsui.data

import android.support.annotation.NonNull
import java.util.*

data class User constructor(@NonNull val name: String, @NonNull val password: String) {
    val id: String = UUID.randomUUID().toString()
}
