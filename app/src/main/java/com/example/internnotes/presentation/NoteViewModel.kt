package com.example.internnotes.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internnotes.data.NoteDao
import com.example.internnotes.data.NoteEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteViewModel(
    private var dao: NoteDao
):ViewModel() {

    private var isSortedByDateAdded = MutableStateFlow(true)
    private var note = isSortedByDateAdded.flatMapLatest {
        if(it){
            dao.getOrderedByDateAdded()
        }else{
            dao.getOrderedByTitle()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    var _state = MutableStateFlow(NoteState())

    var state = combine(_state,isSortedByDateAdded,note){
        state,isSortedByDateAdded,note ->
        state.copy(
           notes = note
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),NoteState())

     fun onEvent(event: NoteEvent){
        when(event){
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    dao.deleteNote(event.note)
                }
            }
            is NoteEvent.SaveNote -> {
                val note = NoteEntity(
                    title = state.value.title.value,
                    description = state.value.description.value,
                    dateAdded = System.currentTimeMillis()
                )
                  viewModelScope.launch {
                      dao.upsertNote(noteEntity =note)
                  }
                _state.update {
                    it.copy(
                        title = mutableStateOf(""),
                        description = mutableStateOf("")
                    )
                }

            }
            NoteEvent.SortEvent -> {
                isSortedByDateAdded.value =! isSortedByDateAdded.value
            }

        }
    }
}