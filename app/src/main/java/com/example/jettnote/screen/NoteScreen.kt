package com.example.jettnote.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jettnote.R
import com.example.jettnote.components.NoteButton
import com.example.jettnote.components.NoteInputText
import com.example.jettnote.components.NoteRow
import com.example.jettnote.data.NotesDataSource
import com.example.jettnote.model.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(modifier: Modifier,
               notes: List<Note> ,
               onAddNote: (Note) -> Unit,
               onRemoveNote: (Note) -> Unit
               )
{
    val title = remember { mutableStateOf("") }
    val description =  remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(modifier = modifier)
    {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.app_name))
            },
            actions = {
                Image(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Icon"
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(Color.LightGray)
        )
        //Content
        Column(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            NoteInputText(
                modifier = Modifier.padding(
                    top = 9.dp,
                    bottom = 8.dp
                ),
                text = title.value,
                label = "Title",
                onTextChange = {
                    if (it.all { char ->
                            char.isLetter() || char.isWhitespace()
                        })
                        title.value = it
                },
                imeAction = ImeAction.Next
            )
            NoteInputText(
                modifier = Modifier.padding(
                    top = 9.dp,
                    bottom = 8.dp
                ),
                text = description.value,
                label = "Add a Note",
                onTextChange = {
                    if (it.all { char ->
                            char.isLetter() || char.isWhitespace()
                        })
                        description.value = it
                },
                imeAction = ImeAction.Done
            )
            NoteButton(
                text = "Save",
                onClick = {
                    if (title.value.isNotEmpty() && description.value.isNotEmpty()) {
                        onAddNote(Note(title = title.value, description = description.value))
                        title.value = ""
                        description.value = ""
                        Toast.makeText(context, "Note Added", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(255, 87, 34))
            )
        }
        Box(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .height(2.dp)
                .background(Color.DarkGray),
        )
        LazyColumn {
            items(notes) {
                NoteRow(
                    note = it,
                    onNoteClicked = {
                        onRemoveNote(it)
                    }
                )
            }
        }
    }
}


