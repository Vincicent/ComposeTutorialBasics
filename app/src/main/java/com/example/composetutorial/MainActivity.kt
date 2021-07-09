package com.example.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetutorial.ui.theme.ComposeTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LetsCompose {
                MyScreenContent()
            }
        }
    }
}

@Composable
fun LetsCompose(content: @Composable () -> Unit) {
    ComposeTutorialTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = Color.LightGray) {
            content()
        }
    }
}

@Composable
fun MyScreenContent(names: List<String> = List(1000) { "Hello Android #$it" }) {
    val counterState = remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxHeight()) {
        NameList(names, Modifier.weight(1f))
        Counter1()
        Counter2(
            count = counterState.value,
            updateCount = { newCount ->
                counterState.value = newCount
            }
        )
    }
}

@Composable
fun Greeting(
    name: String,
    isSelected: Boolean,
    updateIsSelected: () -> Unit
) {
    val backgroundColor by animateColorAsState(if (isSelected) Color.Red else Color.Transparent)

    Text(
        text = name,
        modifier = Modifier
            .padding(24.dp)
            .background(color = backgroundColor)
            .clickable(onClick = { updateIsSelected() }),
        style = MaterialTheme.typography.body1.copy(color = Color.Magenta)
    )
}

@Composable
fun NameList(
    names: List<String>,
    modifier: Modifier = Modifier
) {
    val isSelected = remember { mutableStateListOf<String>() }

    LazyColumn(modifier = modifier) {
        items(items = names) { name ->
            Greeting(
                name = name,
                isSelected = !isSelected.find { it == name }.isNullOrEmpty(),
                updateIsSelected = {
                    if (!isSelected.find { it == name }.isNullOrEmpty()) {
                        isSelected.remove(name)
                    } else {
                        isSelected.add(name)
                    }
                })
            Divider(color = Color.Black)
        }
    }
}

@Composable
fun Counter1() {
    val count = remember {
        mutableStateOf(0)
    }

    Button(
        onClick = { count.value++ },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (count.value % 2 == 0) Color.Yellow else Color.LightGray
        ),
        modifier = Modifier.padding(10.dp)
    ) {
        Text(text = "I've been clicked ${count.value} times :)")
    }
}

@Composable
fun Counter2(
    count: Int,
    updateCount: (Int) -> Unit
) {
    Button(
        onClick = { updateCount(count + 1) },
        modifier = Modifier.padding(10.dp)
    ) {
        Text("I've been clicked $count times")
    }
}

@Preview(showBackground = true, name = "Text preview")
@Composable
fun DefaultPreview() {
    LetsCompose {
        MyScreenContent()
    }
}