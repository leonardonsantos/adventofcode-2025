package org.example

import java.io.File
import java.math.BigInteger

object Day05 {
    data class IdRange(val start: BigInteger, val end: BigInteger)
    data class Input(val ranges: List<IdRange>, val ids: List<BigInteger>)

    fun readInput(filename: String): Input {
        val inputStr = File(filename).readText()
        val parts = inputStr.split("\n\n")
        val rangeLines = parts[0].lines()
        val idLines = parts[1].lines()
        val ranges = rangeLines.map { line ->
            val bounds = line.split("-")
            IdRange(bounds[0].toBigInteger(), bounds[1].toBigInteger())
        }
        val ids = idLines.map { it.toBigInteger() }
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

    fun solution2(input: Input): BigInteger {
        val sortedRanges = input.ranges.sortedWith { a, b -> a.start.compareTo(b.start) }

        val mergedRanges = mutableListOf<IdRange>()
        var newStart = sortedRanges.first().start
        var newEnd: BigInteger? = null
        for (range in sortedRanges) {
            if (newEnd == null) {
                newEnd = range.end
            } else {
                if (range.start <= newEnd + 1.toBigInteger()) {
                    if (range.end > newEnd) {
                        newEnd = range.end
                    }
                } else {
                    mergedRanges.add(IdRange(newStart, newEnd))
                    newStart = range.start
                    newEnd = range.end
                }
            }
        }
        if (newEnd != null) {
            mergedRanges.add(IdRange(newStart, newEnd))
        }

        var count = 0.toBigInteger()
        for (range in mergedRanges) {
            count += range.end - range.start + 1.toBigInteger()
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

    val result2 = Day05.solution2(input)
    println("Solution 2: $result2")
    println()
}