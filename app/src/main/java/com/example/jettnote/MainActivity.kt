package com.example.jettnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jettnote.screen.NoteScreen
import com.example.jettnote.screen.NoteViewModel
import com.example.jettnote.ui.theme.JettNoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JettNoteTheme{
                Scaffold(modifier = Modifier.fillMaxSize())
                { innerPadding ->
                    //val noteViewModel=viewModel<NoteViewModel>()also works
                    val noteViewModel: NoteViewModel by viewModels()
                    NotesApp(noteViewModel=noteViewModel,
                        modifier = Modifier.padding(innerPadding))
                }


            }
        }
    }
}



@Composable
fun NotesApp(noteViewModel: NoteViewModel,

             modifier: Modifier)
{
    var notesList = noteViewModel.noteList.collectAsState().value
    NoteScreen(modifier = modifier,
        notes = notesList,
        onAddNote = {
            noteViewModel.addNote(it)
        },
        onRemoveNote = {
            noteViewModel.removeNote(it)
        }
    )

}