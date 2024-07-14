package com.example.internnotes.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.internnotes.data.NoteEntity

data class NoteState (
    val notes: List<NoteEntity> = emptyList(),
    val title: MutableState<String> = mutableStateOf(""),
    val description: MutableState<String> = mutableStateOf("")
    )