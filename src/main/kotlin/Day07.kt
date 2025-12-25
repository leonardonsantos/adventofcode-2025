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

    data class ParticleCoord(val i: Int, val j: Int)

    fun runParticle(grid: Array<Array<Char>>, i: Int, j: Int, cache: HashMap<ParticleCoord, Long>): Long {
        if (i >= grid.size - 1 || j < 0 || j >= grid[0].size) {
            // end of the path, count one possible timeline
            return 1
        } else {
            // try to go down
            if (grid[i + 1][j] == '.' || grid[i + 1][j] == '|') {
                return runParticle(grid, i + 1, j, cache)
            } else if (grid[i + 1][j] == '^') {
                if (cache.containsKey(ParticleCoord(i+1, j))) {
                    // already computed
                    val cachedCount = cache[ParticleCoord(i+1, j)]!!
                    return cachedCount
                } else {
                    // split
                    val countLeft = runParticle(grid, i + 1, j - 1, cache)
                    val countRight = runParticle(grid, i + 1, j + 1, cache)
                    cache[ParticleCoord(i+1, j)] = countLeft + countRight
                    return countLeft + countRight
                }
            }
        }
        return 0
    }

    fun solution2(grid: Array<Array<Char>>): Long {
        val cache = hashMapOf<ParticleCoord, Long>()

        // find first 'S'
        for (j in grid[0].indices) {
            if (grid[0][j] == 'S') {
                return runParticle(grid, 0, j, cache)
            }
        }
        return 0
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
    println()

    val result2 = Day07.solution2(input)
    println("Solution 2: $result2")
    println()
}