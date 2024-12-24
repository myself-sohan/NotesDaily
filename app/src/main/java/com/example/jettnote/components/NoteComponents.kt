package com.example.jettnote.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertMessage(
    showDialog: MutableState<Boolean>,
    labelText: String,
    leftButtonText : String,
    rightButtonText : String,
    onLeftButtonClick : () -> Unit,
    onRightButtonClick: () -> Unit,
    modifier: Modifier
)
{
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
                modifier = Modifier.padding( 10.dp),)
            {
                Row(modifier= Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = labelText,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                        color = Color.White )
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NoteButton(
                        modifier = Modifier.weight(1f),
                        text = leftButtonText,
                        onClick = onLeftButtonClick,
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    NoteButton(
                        modifier = Modifier.weight(1f),
                        text = rightButtonText,
                        onClick = onRightButtonClick,
                    )
                }


            }
        }
    }
}