package org.example

import java.io.File
import kotlin.math.abs

object Day09 {
    data class Coordinate(val x: Int, val y: Int)

    fun readInput(fileName: String): List<Coordinate> {
        val lines = File(fileName).readLines()
        return lines.map { line ->
            val parts = line.split(",")
            Coordinate(parts[0].toInt(), parts[1].toInt())
        }
    }

    fun solution1(coords: List<Coordinate>): Long {
        var largestArea = 0L
        coords.forEach { from ->
            coords.forEach { to ->
                if (from != to) {
                    val distX = abs(from.x - to.x) + 1L
                    val distY = abs(from.y - to.y) + 1L
                    val area = distX * distY
                    if (area > largestArea) {
                        largestArea = area
                        println("$from -> $to = $area")
                    }
                }
            }
        }
        return largestArea
    }
}

fun main() {
    val input = Day09.readInput("./src/main/resources/day09/input1.txt")
    println(input)
    println()

    val result1 = Day09.solution1(input)
    println("Solution 1: $result1")
    println()
}