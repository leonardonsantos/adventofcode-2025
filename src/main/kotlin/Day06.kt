package org.example

import java.io.File
import kotlin.text.trim

object Day06 {
    data class Problem(val numbers: List<Long>, val operation: Char)

    fun readInput(fileName: String): List<Problem> {
        val lines = File(fileName).readLines()
        val numberLines = Array(lines.size - 1) { Array(0) { 0L } }
        for (i in 0 until lines.size - 1) {
            val line = lines[i].trim().replace("\\s+".toRegex(), " ")
            val numbers = line.split(" ").map { it.toLong() }
            numberLines[i] = numbers.toTypedArray()
        }
        val operations = lines[lines.size - 1].trim().replace("\\s+".toRegex(), " ")
            .split(" ")

        // transpose matrix
        val transposed: Array<Array<Long>> = Array(numberLines[0].size) { Array(numberLines.size) { 0L } }
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

    fun readInput2(fileName: String): List<Problem> {
        val lines: Array<String> = File(fileName).readLines().toTypedArray()
        val operations = lines[lines.size - 1].trim().replace("\\s+".toRegex(), " ")
            .split(" ").map { it[0] } // char at position 0

        val biggestLength = lines.maxOf { it.length }
        val myNumbers: Array<Array<Long?>> = Array(operations.size) { Array(6) { null } }
        var a = 0
        var b = 0
        for (j in 0 until biggestLength) {
            var myNumberStr = ""
            for (i in 0 until lines.size - 1) { // last line is operations
                if (j < lines[i].length) {
                    myNumberStr += lines[i][j]
                }
            }
            myNumberStr = myNumberStr.trim()
            if (myNumberStr.isNotEmpty()) {
                myNumbers[a][b] = myNumberStr.toLong()
                b += 1
            } else {
                a += 1
                b = 0
            }
        }

        var i = 0
        val problems = myNumbers.map { row ->
            val numbers: List<Long> = row.toList().filter { it != null }.map { it!! }
            val operation = operations[i]
            i += 1
            Problem(numbers, operation)
        }

        return problems
    }

    fun solution2(problems: List<Problem>): Long {
        return solution1(problems)
    }
}

fun main() {
    val input = Day06.readInput("./src/main/resources/day06/input1.txt")
    println(input)
    println()

    val result1 = Day06.solution1(input)
    println("Solution 1: $result1")
    println()

    val input2 = Day06.readInput2("./src/main/resources/day06/input1.txt")
    println(input2)
    println()

    val result2 = Day06.solution2(input2)
    println("Solution 2: $result2")
    println()

}