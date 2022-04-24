package com.example.android_note_mvc.model

import java.text.SimpleDateFormat
import java.util.*

data class Note(
    var title: String? = null,
    var body: String? = null,
    val date: String = currentTime(),
    val id: Long? = null
)

private fun currentTime(): String {
    val sdf = SimpleDateFormat("dd/M/yyy hh:mm")
    return sdf.format(Date())
}