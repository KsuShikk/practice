package ci.nsu.mobile.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background

data class ColorItem(
    val name: String,
    val color: Color
)


object Colors {
    val list = listOf(
        ColorItem("red", Color.Red),
        ColorItem("green", Color.Green),
        ColorItem("blue", Color.Blue),
        ColorItem("yellow", Color.Yellow),
        ColorItem("purple", Color.Magenta),
        ColorItem("cyan", Color.Cyan),
        ColorItem("orange", Color(0xFFFFA500)),
        ColorItem("pink", Color(0xFFFFC0CB)),
        ColorItem("brown", Color(0xFF964B00)),
        ColorItem("gray", Color.Gray),
        ColorItem("black", Color.Black),
        ColorItem("white", Color.White)
    )

    val map = list.associateBy { it.name }

    fun find(name: String): ColorItem? = map[name.lowercase()]
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                SimpleColorScreen()
            }
        }
    }
}

@Composable
fun SimpleColorScreen() {
    var text by remember { mutableStateOf("") }
    var foundColor by remember { mutableStateOf<ColorItem?>(null) }
    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(16.dp)
    ) {

        Text(
            text = "Find Color",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                    foundColor = null
                },
                placeholder = { Text("enter color name") },
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {
                    foundColor = Colors.find(text)
                    if (foundColor != null) {
                        Log.d("ColorSearch", "Found: ${foundColor!!.name}")
                    } else {
                        Log.w("ColorSearch", "Not found: $text")
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Go")
            }
        }


        if (foundColor != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(vertical = 16.dp)
                    .clickable {
                        text = foundColor!!.name
                    }
                    .then(Modifier) // чтобы не было ошибки
            ) {

                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(foundColor!!.color)
                )


                Text(
                    text = foundColor!!.name,
                    color = if (foundColor!!.name == "white") Color.Black else Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(8.dp)
                )
            }
        }


        Divider(
            modifier = Modifier.padding(vertical = 16.dp)
        )


        Text(
            text = "All colors:",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )


        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.height(400.dp)
        ) {
            items(Colors.list) { color ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .height(60.dp)
                        .fillMaxWidth()
                        .background(color.color)
                        .clickable {
                            text = color.name
                            foundColor = color
                        }
                ) {
                    Text(
                        text = color.name,
                        color = if (color.name == "white") Color.Black else Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}