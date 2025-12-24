package org.example

import java.io.File

object Day07 {
    fun readInput(fileName: String): Array<Array<Char>> {
        val lines = File(fileName).readLines()
        val grid: Array<Array<Char>> = Array(lines.size) { Array(lines[0].length) { ' ' } }
        var i = 0
        for (line in lines) {
            var j = 0
            for (c: Char in line) {
                grid[i][j] = c
                j += 1
            }
            i += 1
        }
        return grid
    }

    fun printGrid(grid: Array<Array<Char>>) {
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                print(grid[i][j])
            }
            println()
        }
    }

    fun solution1(grid: Array<Array<Char>>): Int {
        var countSplit = 0
        // greedy approach
        for (i in 0 until grid.size - 1) { // no need to check last row
            for (j in grid[i].indices) {
                if (grid[i][j] == 'S' || grid[i][j] == '|') {
                    // try to go down
                    if (grid[i+1][j] == '.') {
                        grid[i+1][j] = '|'
                    } else if (grid[i+1][j] == '^') {
                        // split
                        grid[i+1][j-1] = '|'
                        grid[i+1][j+1] = '|'
                        countSplit += 1
                    }
                }
            }
        }

        return countSplit
    }
}

fun main() {
    val input = Day07.readInput("./src/main/resources/day07/input1.txt")
    Day07.printGrid(input)
    println()

    val result1 = Day07.solution1(input)
    Day07.printGrid(input)
    println()
    println("Solution 1: $result1")
}