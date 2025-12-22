package org.example

import java.io.File

object Day03 {
    data class BatteryBank(val jotageRatings: List<Int>)

    fun readFile(fileName: String): List<String> =
        File(fileName).readLines()

    fun readInput(filename: String): List<BatteryBank> {
        val lines = readFile(filename)
        return lines.map { line ->
            val ratings = line.map { it.code - '0'.code }
            BatteryBank(ratings)
        }
    }

    fun solution1(banks: List<BatteryBank>): Int {
        var totalJotage = 0
        banks.forEach { bank ->
            var maxFirst = -1
            var maxSecond = -1
            var maxJotage = 0
            val count = bank.jotageRatings.indices.count()
            for (i in 0 until count) {
                val first = bank.jotageRatings[i]
                if (first > maxFirst) {
                    maxFirst = first
                    maxSecond = -1
                    for (j in i + 1 until count) {
                        val second = bank.jotageRatings[j]
                        if (second > maxSecond) {
                            maxSecond = second
                        }
                    }
                }
                if (maxSecond != -1) {
                    val jotage = maxFirst * 10 + maxSecond
                    if (jotage > maxJotage) {
                        maxJotage = jotage
                    }
                }
            }
            println("Max jotage for bank is $maxJotage")
            totalJotage += maxJotage
        }
        return totalJotage
    }
}

fun main() {
    val input1 = Day03.readInput("./src/main/resources/day03/input1.txt")
    println(input1)
    val result1 = Day03.solution1(input1)
    println("Solution 1: $result1")
}