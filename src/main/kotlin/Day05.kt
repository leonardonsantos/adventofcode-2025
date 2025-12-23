package org.example

import java.io.File

object Day05 {
    data class IdRange(val start: Long, val end: Long)
    data class Input(val ranges: List<IdRange>, val ids: List<Long>)

    fun readInput(filename: String): Input {
        val inputStr = File(filename).readText()
        val parts = inputStr.split("\n\n")
        val rangeLines = parts[0].lines()
        val idLines = parts[1].lines()
        val ranges = rangeLines.map { line ->
            val bounds = line.split("-")
            IdRange(bounds[0].toLong(), bounds[1].toLong())
        }
        val ids = idLines.map { it.toLong() }
        return Input(ranges, ids)
    }

    fun solution1(input: Input): Int {
        // FIXME: This is a naive solution, optimize later
        var count = 0
        for (id in input.ids) {
            for (range in input.ranges) {
                if (id in range.start..range.end) {
                    count++
                    break
                }
            }
        }
        return count
    }
}

fun main() {
    val input = Day05.readInput("./src/main/resources/day05/input1.txt")
    println(input)
    println()

    val result1 = Day05.solution1(input)
    println("Solution 1: $result1")
    println()
}