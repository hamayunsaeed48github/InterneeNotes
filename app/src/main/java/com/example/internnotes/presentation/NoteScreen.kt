package com.example.internnotes.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.internnotes.ui.theme.Pink80
import kotlin.reflect.KSuspendFunction1

@Composable
fun NoteScreen(
    state: NoteState,
    navController: NavController,
    onEvent: (NoteEvent) -> Unit
){
    Scaffold (
        topBar = {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF6650A3))
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "InternNotes",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                IconButton(onClick = {onEvent(NoteEvent.SortEvent)}) {
                    Icon(
                        imageVector = Icons.Rounded.Sort,
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                            .padding(end = 8.dp),
                        tint = Color.White
                    )
                    
                }

            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                          state.title.value = ""
                    state.description.value = ""
                    navController.navigate("AddNoteScreen")
                },
                containerColor = Color(0xFF6650A3)
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null, tint = Color.White)

            }
        }
    ){
        LazyColumn(
            contentPadding = it,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.notes.size){
                NoteItem(
                    state = state,
                    index = it,
                    onEvent = onEvent,
                )
            }

        }

    }

}

@Composable
fun NoteItem(
    state: NoteState,
    index: Int,
    onEvent: (NoteEvent) -> Unit
) {

    Card (

    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Transparent)
                .padding(8.dp),

            ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = state.notes.get(index = index).title,
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(0.50f)
                )
                IconButton(onClick = {
                    onEvent(NoteEvent.DeleteNote(
                        state.notes.get(index = index)
                    ))
                }) {
                    Icon(imageVector = Icons.Rounded.Delete, contentDescription = null,
                        modifier = Modifier.size(25.dp))
                }

            }

            Text(
                text = state.notes.get(index = index).description,
                fontSize = 16.sp,
                color = Color.Black,
            )


        }
    }




}
