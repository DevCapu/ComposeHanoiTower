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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.devcapu.hanoitower.drag.DraggableItem
import br.com.devcapu.hanoitower.drag.LongPressDraggable
import br.com.devcapu.hanoitower.drop.DropTarget
import br.com.devcapu.hanoitower.ui.UiState
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
                    var uiState by remember { mutableStateOf(UiState()) }
                    val newFirstTower = uiState.firstTower.disks.toMutableList()
                    val newSecondTower = uiState.secondTower.disks.toMutableList()
                    val newThirdTower = uiState.thirdTower.disks.toMutableList()
                    HanoiGame(
                        uiState = uiState,
                        onAddDiskInFirstTower = {
                            if (canMoveDisk(uiState.firstTower.disks, it)) {
                                newFirstTower.add(it)
                                if (uiState.secondTower.disks.contains(it)) {
                                    newSecondTower.remove(it)
                                } else if (uiState.thirdTower.disks.contains(it)) {
                                    newThirdTower.remove(it)
                                }
                                uiState = uiState.copy(
                                    firstTower = Tower(newFirstTower),
                                    secondTower = Tower(newSecondTower),
                                    thirdTower = Tower(newThirdTower)
                                )
                            }
                        },
                        onAddDiskInSecondTower = {
                            if (canMoveDisk(uiState.secondTower.disks, it)) {
                                newSecondTower.add(it)
                                if (uiState.firstTower.disks.contains(it)) {
                                    newFirstTower.remove(it)
                                } else if (uiState.thirdTower.disks.contains(it)) {
                                    newThirdTower.remove(it)
                                }
                                uiState = uiState.copy(
                                    firstTower = Tower(newFirstTower),
                                    secondTower = Tower(newSecondTower),
                                    thirdTower = Tower(newThirdTower)
                                )
                            }
                        },
                        onAddDiskInThirdTower = {
                            if (canMoveDisk(uiState.thirdTower.disks, it)) {
                                newThirdTower.add(it)
                                if (uiState.firstTower.disks.contains(it)) {
                                    newFirstTower.remove(it)
                                } else if (uiState.secondTower.disks.contains(it)) {
                                    newSecondTower.remove(it)
                                }
                                uiState = uiState.copy(
                                    firstTower = Tower(newFirstTower),
                                    secondTower = Tower(newSecondTower),
                                    thirdTower = Tower(newThirdTower)
                                )
                            }
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun HanoiGame(
    uiState: UiState,
    onAddDiskInFirstTower: (Disk) -> Unit = { },
    onAddDiskInSecondTower: (Disk) -> Unit = { },
    onAddDiskInThirdTower: (Disk) -> Unit = { }
) {
    LongPressDraggable(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan)
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 64.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                Stick(disks = uiState.firstTower.disks, onAddDisk = onAddDiskInFirstTower)
                Stick(disks = uiState.secondTower.disks, onAddDisk = onAddDiskInSecondTower)
                Stick(disks = uiState.thirdTower.disks, onAddDisk = onAddDiskInThirdTower)
            }
        }
    }
}

@Composable
private fun Stick(
    disks: List<Disk>,
    onAddDisk: (Disk) -> Unit
) {
    DropTarget<Disk>(modifier = Modifier) { isInbound, disk ->
        disk?.let {
            if (isInbound) {
                onAddDisk(disk)
            }
        }
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

            disks.forEachIndexed { index, disk ->
                DraggableItem(modifier = Modifier, dataToDrop = disk) {
                    val width = Dp(((index * 40) + 100).toFloat())
                    Disk(width)
                }
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

private fun canMoveDisk(disksAlreadyOnStick: List<Disk>, diskToBeAdded: Disk): Boolean {
    return disksAlreadyOnStick.isEmpty() || (!disksAlreadyOnStick.contains(diskToBeAdded) && disksAlreadyOnStick.last().size > diskToBeAdded.size)
}

@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 720, heightDp = 360)
@Composable
fun DefaultPreview() {
    ComposeHanoiTowerTheme {
        HanoiGame(UiState())
    }
}