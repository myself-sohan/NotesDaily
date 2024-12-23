package com.example.jettnote.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jettnote.R
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
    var showDialog= remember { mutableStateOf(false) }
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
//                NoteButton(
//                    text = "Press for 2 sec to Delete All",
//                    onClick = {
//                        noteViewModel.removeAllNote()
//                        Toast.makeText(context, "All Notes Deleted", Toast.LENGTH_SHORT).show()
//                    },
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(232, 69, 61))
//                )
                NoteButton(
                    text = "Delete All",
                    onClick = {
                        showDialog.value = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(232, 69, 61))
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

        if (showDialog.value) {
            BasicAlertDialog(
                onDismissRequest = { showDialog.value = false },
                )
            {
                Card(modifier=Modifier
                    .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(Color(62, 118, 213, 255)),
                    elevation = CardDefaults.cardElevation(10.dp)
                ) {
                    Column(verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(top = 10.dp),)
                    {
                        Row(modifier= Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Are you sure you want to permanently delete all notes?",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center,
                                color = Color.White )
                        }
                        Row(
                            modifier = modifier
                                .padding(20.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            NoteButton(
                                modifier = Modifier.weight(1f),
                                text = "Yes",
                                onClick = {
                                    noteViewModel.removeAllNote()
                                    Toast.makeText(context, "All Notes Deleted", Toast.LENGTH_SHORT)
                                        .show()
                                    showDialog.value = false
                                },
                            )
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(2f)
                            )
                            NoteButton(
                                modifier = Modifier.weight(1f),
                                text = "No",
                                onClick = {
                                    showDialog.value = false
                                },
                            )
                        }


                    }
                }
            }
        }
    }
}


