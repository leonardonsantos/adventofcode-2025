package org.example

import java.io.File
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

object Day09 {
    data class Coordinate(val x: Int, val y: Int)
    data class Edge(val from: Coordinate, val to: Coordinate)

    fun readInput(fileName: String): List<Coordinate> {
        val lines = File(fileName).readLines()
        return lines.map { line ->
            val parts = line.split(",")
            Coordinate(parts[0].toInt(), parts[1].toInt())
        }
    }

    fun calculateArea(from: Coordinate, to: Coordinate): Long {
        val distX = abs(from.x - to.x) + 1L
        val distY = abs(from.y - to.y) + 1L
        return distX * distY
    }

    fun solution1(coords: List<Coordinate>): Long {
        var largestArea = 0L
        coords.forEach { from ->
            coords.forEach { to ->
                if (from != to) {
                    val area = calculateArea(from, to)
                    if (area > largestArea) {
                        largestArea = area
                        println("$from -> $to = $area")
                    }
                }
            }
        }
        return largestArea
    }

    fun isWithinPolygon(point: Coordinate, edges: List<Edge>): Boolean {
        // move to four directions and try to find an edge

        var left = false
        var right = false
        var up = false
        var down = false

        edges.forEach { edge ->
            if ((point.x == edge.from.x && point.y == edge.from.y)
                ||(point.x == edge.to.x && point.y == edge.to.y)) {
                return true
            } else {
                // vertical edges
                if (edge.from.x == edge.to.x) {
                    val xEdge = edge.from.x
                    // check if point.y is within edge y range
                    val minY = min(edge.from.y, edge.to.y)
                    val maxY = max(edge.from.y, edge.to.y)
                    if (point.y in minY..maxY) {
                        if (xEdge <= point.x) {
                            left = true
                        } else {
                            right = true
                        }
                    }
                } else if (edge.from.y == edge.to.y) {
                    // horizontal edges
                    val yEdge = edge.from.y
                    // check if point.x is within edge x range
                    val minX = min(edge.from.x, edge.to.x)
                    val maxX = max(edge.from.x, edge.to.x)
                    if (point.x in minX..maxX) {
                        if (yEdge <= point.y) {
                            up = true
                        } else {
                            down = true
                        }
                    }
                }
            }

        }

        return left && right && up && down
    }

    fun solution2(coords: List<Coordinate>): Long {
        // for every rectangle, check if any of its four corners are not the polygon

        val edges = coords.mapIndexed { index, coord ->
            val nextCoord = coords[(index + 1) % coords.size]
            Edge(
                Coordinate(coord.x, coord.y),
                Coordinate(nextCoord.x, nextCoord.y)
            )
        }

        var largestArea = 0L
        coords.forEach { from ->
            coords.forEach { to ->
                if (from != to) {
                    val minX = min(from.x, to.x)
                    val maxX = max(from.x, to.x)
                    val minY = min(from.y, to.y)
                    val maxY = max(from.y, to.y)

                    // all four corners must be within the polygon
                    if (isWithinPolygon(Coordinate(minX, minY), edges)
                        && isWithinPolygon(Coordinate(minX, maxY), edges)
                        && isWithinPolygon(Coordinate(maxX, minY), edges)
                        && isWithinPolygon(Coordinate(maxX, maxY), edges)) {
                        val area = calculateArea(from, to)
                        println("$from -> $to = $area")
                        if (area > largestArea) {
                            largestArea = area
                        }
                    }
                }
            }
        }
        return largestArea
    }
}

fun main() {
    val input = Day09.readInput("./src/main/resources/day09/input1.txt")
    println(input)
    println()

    val result1 = Day09.solution1(input)
    println("Solution 1: $result1")
    println()

    val result2 = Day09.solution2(input)
    println("Solution 2: $result2") // Wrong result: 4629432496
    println()
}