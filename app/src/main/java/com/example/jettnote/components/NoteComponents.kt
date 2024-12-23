package com.example.jettnote.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction


@Composable
fun NoteInputText(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    maxLine: Int = 1,
    onTextChange: (String) -> Unit,
    onImeAction: () -> Unit = {},
    imeAction: ImeAction
)
{
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = text,
        onValueChange = onTextChange,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(33, 37, 41),             // Charcoal Black (Focused Text)
            unfocusedTextColor = Color(123, 12, 202, 255),        // Neutral Gray (Unfocused Text)
            focusedContainerColor = Color(255, 248, 240),     // Soft Beige/Ivory (Focused Background)
            unfocusedContainerColor = Color(245, 245, 245),   // Light Neutral Gray (Unfocused Background)
            focusedLabelColor = Color(117, 56, 0),            // Warm Brown (Focused Label)
            unfocusedLabelColor = Color(13, 13, 13)
        ),
        label = { Text(text = label) },
        maxLines = maxLine,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction =imeAction),
        keyboardActions = KeyboardActions(
            onDone =
            {
                onImeAction()
                keyboardController?.hide()
            }),
        modifier = modifier
    )
}


@Composable
fun NoteButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    colors: ButtonColors  = ButtonDefaults.buttonColors()
)
{
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = CircleShape,
        colors = colors
    )
    {
        Text(text = text,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
    }
}