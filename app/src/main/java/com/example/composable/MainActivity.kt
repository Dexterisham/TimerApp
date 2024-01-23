package com.example.composable

import android.os.Bundle
import android.os.Message
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composable.ui.theme.ComposableTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposableTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
TimerScreen()


                }
            }
        }
    }
}


@Composable
fun TimerScreen() {
    var timerValue by remember { mutableStateOf(0) }
    var isTimerRunning by remember { mutableStateOf(false) }


    // CoroutineScope for handling the timer logic
    val coroutineScope = rememberCoroutineScope()
    // CoroutineScope for handling the timer logic
    var timerJob by remember { mutableStateOf<Job?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,


    ) {
        Text(text = "$timerValue seconds", fontSize = 24.sp,modifier=Modifier,color=Color.White)

        Button(
            onClick = {
                isTimerRunning = true

                // Start or stop the timer based on the button click
                if (isTimerRunning) {
                    startTimer(coroutineScope) {
                        timerValue = it
                    }
                } else {
                    stopTimer(timerJob)
                }
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(50f)
                //.height(50.dp)
                //.width(90.dp)
        ) {
            Text(
               // modifier = Modifier.size(12.dp),
                text =
            if (isTimerRunning)
                "Stop"
            else
                "Start")
        }

        Button(
            onClick = {
                resetTimer { timerValue = 0 }
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Reset")
        }

    }
}

// Coroutine function to start the timer
private fun startTimer(scope: CoroutineScope, onTick: (Int) -> Unit) {
    scope.launch {
        var seconds = 0
        while (true) {
            delay(1000) // Delay for 1 second
            withContext(Dispatchers.Main) {
                onTick(seconds++)
            }
        }
    }
}

// Function to stop the timer
private fun stopTimer(timerJob: Job?) {
    // The coroutine will continue running, but the timer won't be updated
    // Function to stop the timer
        timerJob?.cancel()


}

// Function to reset the timer
private fun resetTimer(onReset: () -> Unit) {
    onReset()
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    ComposableTheme {
        TimerScreen()
    }
}