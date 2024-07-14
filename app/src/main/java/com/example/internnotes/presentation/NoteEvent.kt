package com.example.internnotes.presentation

import com.example.internnotes.data.NoteEntity

sealed interface NoteEvent {

     object SortEvent:NoteEvent
     data class DeleteNote(var note:NoteEntity):NoteEvent
    data class SaveNote(
        var title:String,
        var description:String
    ):NoteEvent
}