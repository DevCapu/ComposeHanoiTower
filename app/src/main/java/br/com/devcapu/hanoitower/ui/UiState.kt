package br.com.devcapu.hanoitower.ui

import androidx.compose.ui.graphics.Color
import br.com.devcapu.hanoitower.Disk
import br.com.devcapu.hanoitower.Tower

data class UiState(
    val firstTower: Tower = Tower(
        disks = listOf(
            Disk(
                color = Color.Green,
                size = 1,
            ),
            Disk(
                color = Color.Blue,
                size = 2,
            ),
            Disk(
                color = Color.Red,
                size = 3,
            ),
        ).sortedBy { it.size }.reversed()
    ),
    val secondTower: Tower = Tower(),
    val thirdTower: Tower = Tower()
)