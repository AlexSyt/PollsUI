package com.example.alex.pollsui.data

import java.util.*

data class User constructor(val name: String, val password: String) {
    val id: String = UUID.randomUUID().toString()
}
