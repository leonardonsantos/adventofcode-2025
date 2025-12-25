package org.example

import org.example.Day08.calculateDistances
import java.io.File
import kotlin.math.pow
import kotlin.math.sqrt

object Day08 {
    data class Coordinate(val x: Int, val y: Int, val z: Int)
    data class CoordinatePath(val distance: Double, var connected: Boolean = false)
    data class MyHash(val distances: HashMap<Coordinate, HashMap<Coordinate, CoordinatePath>>)

    fun readInput(fileName: String): List<Coordinate> {
        val lines = File(fileName).readLines()
        return lines.map { line ->
            val parts = line.split(",")
            Coordinate(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
        }
    }

    fun calculateDistance(from: Coordinate, to: Coordinate): Double {
        return sqrt(
            ((from.x - to.x).toDouble().pow(2.0)) +
                    ((from.y - to.y).toDouble().pow(2.0)) +
                    ((from.z - to.z).toDouble().pow(2.0))
        )
    }

    fun calculateDistances(coord: List<Coordinate>): MyHash {
        val hash = MyHash(HashMap())
        for (i in coord.indices) {
            val from = coord[i]
            val mapTo = HashMap<Coordinate, CoordinatePath>()
            for (j in coord.indices) {
                if (i != j) {
                    val to = coord[j]
                    val distance = calculateDistance(from, to)
                    mapTo[to] = CoordinatePath(distance)
                }
            }
            hash.distances[from] = mapTo
        }
        return hash
    }

    fun calculateShortestConnections(hash: MyHash, connections: Int) {
        (0 until connections).forEach { _ ->
            // find the shortest distance
            var minDistance = Double.MAX_VALUE
            var minFrom: Coordinate? = null
            var minTo: Coordinate? = null
            for ((from, mapTo) in hash.distances) {
                for ((to, path) in mapTo) {
                    if (!path.connected && path.distance < minDistance) {
                        minDistance = path.distance
                        minFrom = from
                        minTo = to
                    }
                }
            }
            if (minFrom != null && minTo != null) {
                // mark as connected
                hash.distances[minFrom]?.get(minTo)?.connected = true
                hash.distances[minTo]?.get(minFrom)?.connected = true
            }
        }
    }

    fun connectedComponents(hash: MyHash): Set<List<Coordinate>> {
        val visited = mutableSetOf<Coordinate>()
        val components = mutableSetOf<List<Coordinate>>()

        // DFS to find connected components
        for (start in hash.distances.keys) {
            if (!visited.contains(start)) {
                val component = mutableListOf<Coordinate>()
                val stack = ArrayDeque<Coordinate>()
                stack.addLast(start)
                while (stack.isNotEmpty()) {
                    val node = stack.removeLast()
                    if (!visited.contains(node)) {
                        visited.add(node)
                        component.add(node)
                        val neighbors = hash.distances[node]?.filter { it.value.connected }?.keys ?: emptySet()
                        for (neighbor in neighbors) {
                            if (!visited.contains(neighbor)) {
                                stack.add(neighbor)
                            }
                        }
                    }
                }
                components.add(component)
            }
        }
        return components
    }

    fun solution1(hash: MyHash, connections: Int): Long {
        calculateShortestConnections(hash, connections)
        val components = connectedComponents(hash)
        println("Found ${components.size} connected components")

        val sizes = components
            .map { it.size }
            .sortedDescending()
        val solution = sizes.take(3)
            .map { it.toLong() }
            .reduce { acc, i -> acc * i }

        return solution
    }

    fun allConnected(hash: MyHash): Boolean {
        val components = connectedComponents(hash)
        return components.size == 1
    }

    fun solution2(hash: MyHash): Long {
        var minFrom: Coordinate? = null
        var minTo: Coordinate? = null

        // FIXME: inefficient implementation
        while(!allConnected(hash)) {
            // find the shortest distance
            var minDistance = Double.MAX_VALUE
            minFrom = null
            minTo = null
            for ((from, mapTo) in hash.distances) {
                for ((to, path) in mapTo) {
                    if (!path.connected && path.distance < minDistance) {
                        minDistance = path.distance
                        minFrom = from
                        minTo = to
                    }
                }
            }
            if (minFrom != null && minTo != null) {
                // mark as connected
                hash.distances[minFrom]?.get(minTo)?.connected = true
                hash.distances[minTo]?.get(minFrom)?.connected = true
            }
        }

        return minFrom!!.x.toLong() * minTo!!.x.toLong()
    }

}

fun main() {
    val input1 = Day08.readInput("./src/main/resources/day08/input1.txt")
    println(input1)
    println()

    val hash = calculateDistances(input1)

    val result1 = Day08.solution1(hash, 1000)
    println("Solution 1: $result1")
    println()

    val result2 = Day08.solution2(hash)
    println("Solution 2: $result2")
    println()
}