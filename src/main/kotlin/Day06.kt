package org.example

import java.io.File

object Day06 {
    data class Problem(val numbers: List<Long>, val operation: Char)

    fun readInput(fileName: String): List<Problem> {
        val lines = File(fileName).readLines()
        val numberLines: Array<Array<Long>> = Array(lines.size - 1) { Array<Long>(0) { 0L } }
        for (i in 0 until lines.size - 1) {
            val line = lines[i].trim().replace("\\s+".toRegex(), " ")
            val numbers = line.split(" ").map { it.toLong() }
            numberLines[i] = numbers.toTypedArray()
        }
        val operations = lines[lines.size - 1].trim().replace("\\s+".toRegex(), " ").split(" ")

        // transpose matrix
        val transposed: Array<Array<Long>> = Array(numberLines[0].size) { Array<Long>(numberLines.size) { 0L } }
        for (i in 0 until numberLines.size) {
            for (j in 0 until numberLines[i].size) {
                transposed[j][i] = numberLines[i][j]
            }
        }

        var i = 0
        return transposed.map { row ->
            val problem = Problem(row.toList(), operations[i][0]) // char at position 0
            i += 1
            problem
        }
    }

    fun solution1(problems: List<Problem>): Long {
        var total = 0L
        for (problem in problems) {
            val result = when (problem.operation) {
                '+' -> problem.numbers.sum()
                '*' -> problem.numbers.fold(1L) { acc, n -> acc * n }
                else -> 0L
            }
            total += result
        }
        return total
    }
}

fun main() {
    val input = Day06.readInput("./src/main/resources/day06/input1.txt")
    println(input)
    println()

    val result1 = Day06.solution1(input)
    println("Solution 1: $result1")
    println()
}