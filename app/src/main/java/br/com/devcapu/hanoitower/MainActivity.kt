package br.com.devcapu.hanoitower

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.devcapu.hanoitower.ui.theme.ComposeHanoiTowerTheme
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeHanoiTowerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HanoiGame()
                }
            }
        }
    }
}

@Composable
fun HanoiGame() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan),
    ) {
        Row(
            modifier = Modifier
                .padding(top = 64.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            Stick(3)
            Stick(1)
            Stick(2)
        }
    }
}

@Composable
private fun Stick(numberOfDisks: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//      Stick
        Spacer(
            modifier = Modifier
                .background(Color(0xFF4E3825))
                .border(
                    width = 2.dp,
                    color = Color(0xFF2C2016),
                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                )
                .width(12.dp)
                .height(60.dp)
        )

        repeat(numberOfDisks) {
            val width = Dp(((it * 40) + 100).toFloat())
            Disk(width)
        }
//      Base
        Spacer(
            modifier = Modifier
                .background(
                    color = Color(0xFF4E3825),
                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                )
                .border(
                    width = 2.dp,
                    color = Color(0xFF2C2016),
                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                )
                .width(64.dp)
                .height(20.dp)
        )
    }
}

@Composable
fun Disk(width: Dp) {
    val color by remember {
        mutableStateOf(
            Color(
                Random.nextInt(256),
                Random.nextInt(256),
                Random.nextInt(256)
            )
        )
    }
    Spacer(
        modifier = Modifier
            .background(
                color = color,
                shape = RoundedCornerShape(8.dp)
            )
            .width(width)
            .height(28.dp)
    )
}

@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 720, heightDp = 360)
@Composable
fun DefaultPreview() {
    ComposeHanoiTowerTheme {
        HanoiGame()
    }
}