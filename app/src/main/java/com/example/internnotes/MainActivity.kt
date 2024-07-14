package com.example.internnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.internnotes.data.NoteDatabase
import com.example.internnotes.presentation.AddNoteScreen
import com.example.internnotes.presentation.NoteScreen
import com.example.internnotes.presentation.NoteViewModel
import com.example.internnotes.ui.theme.InternNotesTheme

class MainActivity : ComponentActivity() {
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java,
            "notes.dp"
        ).build()
    }
    private val viewModel by viewModels<NoteViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NoteViewModel(database.dao) as T
                }
            }
        }
        )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InternNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by viewModel.state.collectAsState()
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "NoteScreen"){
                        composable("NoteScreen"){
                            NoteScreen(state = state, navController = navController, onEvent = viewModel::onEvent)


                        }
                        composable("AddNoteScreen"){
                            AddNoteScreen(state = state, navController = navController, onEvent = viewModel::onEvent)


                        }
                    }

                }
            }
        }
    }
}

