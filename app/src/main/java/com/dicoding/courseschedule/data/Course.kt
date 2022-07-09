package com.dicoding.courseschedule.data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

//TODO 1 : Define a local database table using the schema in app/schema/course.json (DONE)

@Entity
data class Course(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val courseName: String,
    val day: Int,
    val startTime: String,
    val endTime: String,
    val lecturer: String,
    val note: String
)
