package com.example.jettnote.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.jettnote.R
import com.example.jettnote.components.AlertMessage
import com.example.jettnote.components.NoteButton
import com.example.jettnote.components.NoteInputText
import com.example.jettnote.components.NoteRow
import com.example.jettnote.model.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(modifier: Modifier,
               noteViewModel: NoteViewModel,
               onAddNote: (Note) -> Unit,
               onRemoveNote: (Note) -> Unit
               )
{
    val notes = noteViewModel.noteList.collectAsState().value
    val title = remember { mutableStateOf("") }
    val description =  remember { mutableStateOf("") }
    val context = LocalContext.current
    var showDialogForDeleteAll= remember { mutableStateOf(false) }
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
                            char.isLetter() || char.isWhitespace() || char.isDigit()
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
                            char.isLetter() || char.isWhitespace() || char.isDigit()
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
                colors = ButtonDefaults.buttonColors(containerColor = Color(134, 222, 92, 255))
            )
        }
        Box(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .height(2.dp)
                .background(Color.DarkGray),
        )
        if (notes.isEmpty()) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(10.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                )
                {
                    Text(
                        text = "No Notes available",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally)
            {
                NoteButton(
                    text = "Delete All",
                    onClick = {
                        showDialogForDeleteAll.value = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(232, 69, 61))
                )
                LazyColumn {
                    items(notes) {
                        NoteRow(
                            note = it,
                            onNoteClicked = {
                                onRemoveNote(it)
                            },
                        )
                    }
                }
            }

        }

        if (showDialogForDeleteAll.value) {
            AlertMessage(
                labelText = "Are you sure you want to delete all notes?",
                leftButtonText = "YES",
                rightButtonText = "NO",
                onLeftButtonClick = {
                    noteViewModel.removeAllNote()
                    Toast.makeText(context, "All Notes Deleted", Toast.LENGTH_SHORT)
                        .show()
                    showDialogForDeleteAll.value = false
                },
                onRightButtonClick = {
                    showDialogForDeleteAll.value=false
                },
                showDialog = showDialogForDeleteAll,
                modifier = modifier
            )
        }
    }
}


