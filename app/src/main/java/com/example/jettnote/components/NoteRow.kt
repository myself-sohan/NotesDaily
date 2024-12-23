package com.example.jettnote.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.jettnote.model.Note
import com.example.jettnote.utils.formatDate
import java.time.format.DateTimeFormatter

@Composable
fun NoteRow(
    note: Note,
    onNoteClicked: (Note) -> Unit
)
{
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(topEndPercent = 33, bottomStartPercent = 33))
            .fillMaxWidth(),
        color = Color(0xFFAEC1CE),
        shadowElevation = 6.dp
    )
    {
        Column(
            modifier = Modifier
                .padding(horizontal = 14.dp, vertical = 6.dp)
                .clickable {
                    onNoteClicked(note)
                },
            horizontalAlignment = Alignment.Companion.Start
        )
        {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = note.description,
                style = MaterialTheme.typography.titleSmall
            )
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,) {
                Text(
                    text = formatDate(note.entryDate.time),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}