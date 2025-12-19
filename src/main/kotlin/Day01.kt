package org.example

import java.io.File

object Day01 {
    data class Rotation(val direction: Char, val clicks: Int)

    fun readFile(fileName: String): List<String> =
        File(fileName).readLines()

    fun readInput(filename: String): List<Rotation> {
        val lines = readFile(filename)
        return lines.map { line ->
            val direction = line[0]
            val clicks = line.substring(1).toInt()
            Rotation(direction, clicks)
        }
    }

    fun solution1(rotations: List<Rotation>): Int {
        // The dial starts by pointing at 50
        var position = 50
        var count = 0
        for (rotation in rotations) {
            position += if (rotation.direction == 'R') rotation.clicks else -rotation.clicks
            if (position > 99)
                position -= 100
            if (position < 0)
                position += 100
            position %= 100
            if (position == 0)
                count++
            println("Position: $position")
        }
        return count
    }

    fun solution2(rotations: List<Rotation>): Int {
        // The dial starts by pointing at 50
        var position = 50
        var count = 0
        for (rotation in rotations) {
            position += if (rotation.direction == 'R') rotation.clicks else -rotation.clicks
            while (position > 99) {
                position -= 100
                count++
            }
            while (position < 0) {
                position += 100
                count++
            }
            println("Position: $position")
        }
        return count
    }
}

fun main() {
    val input1 = Day01.readInput("./src/main/resources/day01/input1.txt")
    print(input1)
    val result1 = Day01.solution1(input1)
    println("Result 1: $result1")
    val result2 = Day01.solution2(input1)
    println("Result 2: $result2")
}