package com.example.internnotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val title:String,
    val description:String,
    val dateAdded:Long

)
