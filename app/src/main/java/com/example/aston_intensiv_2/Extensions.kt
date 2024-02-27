package com.example.aston_intensiv_2

import android.graphics.Color
import kotlin.random.Random

fun colorOf(string: String) = Color.parseColor(string)

fun randomRotation() = Random.nextFloat() * 3600f
fun randomUri(): String {
    val uris = listOf(
        "https://placekitten.com/300/300",
        "https://loremflickr.com/300/300",
        "https://placebeard.it/300/300"
    )
    return uris.random()
}

fun hardCodedData() = listOf(
    Pair("RED", Color.RED),
    Pair("ORANGE", colorOf("#FFA500")),
    Pair("YELLOW", Color.YELLOW),
    Pair("GREEN", Color.GREEN),
    Pair("CYAN", Color.CYAN),
    Pair("BLUE", Color.BLUE),
    Pair("MAGENTA", Color.MAGENTA)
)

fun calculateSliceIndex(rotation: Float): Int {
    val sliceSize = 360f / hardCodedData().size
    val actualRotation = ((-rotation + sliceSize / 2) % 360f + 360f)
    return (actualRotation / sliceSize).toInt()
}

fun getNameFromData(index: Int): String = hardCodedData().get(index).first
