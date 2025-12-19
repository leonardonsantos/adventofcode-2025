package org.example

import java.io.File

object Day02 {
    data class Range(val start: Long, val end: Long)

    fun readInput(filename: String): List<Range> =
        File(filename).readText().split(",").map { rangeStr ->
            val bounds = rangeStr.split("-")
            val range = Range(bounds[0].toLong(), bounds[1].toLong())
            range
        }

    fun solution1(ranges: List<Range>): Long {
        var sum: Long = 0
        ranges.forEach { range ->
            for (i in range.start..range.end) {
                val valueStr = i.toString()
                if (valueStr.length % 2 == 0) {
                    val mid = valueStr.length / 2
                    val firstHalf = valueStr.substring(0, mid)
                    val secondHalf = valueStr.substring(mid)
                    if (firstHalf == secondHalf) {
                        sum += i
                    }
                }
            }
        }
        return sum
    }

    fun isRepeatedSequence(s: String): Boolean {
        val len = s.length
        for (subLen in 1..len / 2) {
            if (len % subLen == 0) {
                val subStr = s.take(subLen)
                val repeatedStr = subStr.repeat(len / subLen)
                if (repeatedStr == s) {
                    return true
                }
            }
        }
        return false
    }

    fun solution2(ranges: List<Range>): Long {
        var sum: Long = 0
        ranges.forEach { range ->
            for (i in range.start..range.end) {
                val valueStr = i.toString()
                if (isRepeatedSequence(valueStr)) {
                    sum += i
                }
            }
        }
        return sum
    }
}

fun main() {
    val input1 = Day02.readInput("./src/main/resources/day02/input1.txt")
    println(input1)
    val result1 = Day02.solution1(input1)
    println("Result 1: $result1")
    val result2 = Day02.solution2(input1)
    println("Result 2: $result2")
}