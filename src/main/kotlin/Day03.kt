package org.example

import java.io.File
import kotlin.math.pow

object Day03 {
    data class BatteryBank(val jotageRatings: List<Long>)

    fun readFile(fileName: String): List<String> =
        File(fileName).readLines()

    fun readInput(filename: String): List<BatteryBank> {
        val lines = readFile(filename)
        return lines.map { line ->
            val ratings = line.map { (it.code - '0'.code).toLong() }
            BatteryBank(ratings)
        }
    }

    fun solution1(banks: List<BatteryBank>): Long {
        var totalJotage = 0L
        banks.forEach { bank ->
            var maxFirst = -1L
            var maxSecond = -1L
            var maxJotage = 0L
            val count = bank.jotageRatings.size
            for (i in 0 until count) {
                val first = bank.jotageRatings[i]
                if (first > maxFirst) {
                    maxFirst = first
                    maxSecond = -1L
                    for (j in i + 1 until count) {
                        val second = bank.jotageRatings[j]
                        if (second > maxSecond) {
                            maxSecond = second
                        }
                    }
                }
                if (maxSecond != -1L) {
                    val jotage = maxFirst * 10L + maxSecond
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

    fun getMaxJotage(ratings: List<Long>, length: Int, start: Int = 0): Long? {
        val count = ratings.size
        if ((length == 0) || (start >= count)) {
            return null
        }
        var maxFirst: Long? = null
        var maxJotage: Long? = null
        for (i in start until count) {
            val first = ratings[i]
            if (maxFirst == null || first > maxFirst) {
                maxFirst = first
                val valueRest = getMaxJotage(ratings, length - 1, i + 1)
                if (valueRest != null) {
                    val jotage = maxFirst * (10.0.pow(length - 1).toLong()) + valueRest
                    if (maxJotage == null || jotage > maxJotage) {
                        maxJotage = jotage
                    }
                } else if (length == 1) {
                    if (maxJotage == null || maxFirst > maxJotage) {
                        maxJotage = maxFirst
                    }
                }
            }
        }
        return maxJotage
    }

    fun solution1b(banks: List<BatteryBank>): Long {
        var totalJotage = 0L
        banks.forEach { bank ->
            val maxJotage = getMaxJotage(bank.jotageRatings, 2)
            if (maxJotage != null) {
                println("Max jotage for bank is $maxJotage")
                totalJotage += maxJotage
            } else {
                println("No valid jotage for bank")
            }
        }
        return totalJotage
    }

    fun solution2(banks: List<BatteryBank>): Long {
        var totalJotage = 0L
        banks.forEach { bank ->
            val maxJotage = getMaxJotage(bank.jotageRatings, 12)
            if (maxJotage != null) {
                println("Max jotage for bank is $maxJotage")
                totalJotage += maxJotage
            } else {
                println("No valid jotage for bank")
            }
        }
        return totalJotage
    }
}

fun main() {
    val input1 = Day03.readInput("./src/main/resources/day03/input1.txt")

    val result1 = Day03.solution1(input1)
    println("Solution 1: $result1")
    println()

    val result1b = Day03.solution1b(input1)
    println("Solution 1b: $result1b")
    println()

    val result2 = Day03.solution2(input1)
    println("Solution 2: $result2")
}