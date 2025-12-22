package org.example

import java.io.File

class Day04 {
    data class Rolls(val positions: Array<Array<Char>>) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as Rolls
            return positions.contentDeepEquals(other.positions)
        }

        override fun hashCode(): Int {
            return positions.contentDeepHashCode()
        }

        override fun toString(): String {
            val sb = StringBuilder()
            for (row in positions) {
                for (ch in row) {
                    sb.append(ch)
                }
                sb.append("\n")
            }
            return sb.toString()
        }

        fun isRollAt(i: Int, j: Int): Boolean {
            val rowCount = positions.size
            val colCount = positions[0].size
            if (i < 0 || i >= rowCount || j < 0 || j >= colCount) {
                return false
            }
            return positions[i][j] == '@'
        }

        fun countAdjacentRolls(i: Int, j: Int): Int {
            var count = 0
            for (di in -1..1) {
                for (dj in -1..1) {
                    if (di == 0 && dj == 0) {
                        continue
                    }
                    if (isRollAt(i + di, j + dj)) {
                        count++
                    }
                }
            }
            return count
        }
    }

    fun readFile(fileName: String): List<String> =
        File(fileName).readLines()

    fun readInput(filename: String): Rolls {
        val lines = readFile(filename)
        val positions = Array(lines.size) { i ->
            lines[i].toCharArray().toTypedArray()
        }
        return Rolls(positions)
    }

    fun solution1(rolls: Rolls): Int {
        var count = 0
        for (i in rolls.positions.indices) {
            for (j in rolls.positions[i].indices) {
                val ch = rolls.positions[i][j]
                if (ch == '@') {
                    if (rolls.countAdjacentRolls(i, j) < 4) {
                        count++
                    }
                }
            }
        }
        return count
    }
}

fun main() {
    val input1 = Day04().readInput("./src/main/resources/day04/input1.txt")
    println(input1)
    println()

    val result1 = Day04().solution1(input1)
    println("Solution 1: $result1")
}