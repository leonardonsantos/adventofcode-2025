package org.example

import java.io.File

object Day11 {
    data class Vertex(val name: String)
    data class Hash(val vertices: HashMap<Vertex, List<Vertex>>)

    fun readInput(fileName: String): Hash {
        val lines = File(fileName).readLines()
        val vertices = HashMap<Vertex, List<Vertex>>()
        for (line in lines) {
            val parts = line.split(": ")
            val from = Vertex(parts[0])
            val toList = parts[1].split(" ").map { Vertex(it) }
            vertices[from] = toList
        }
        return Hash(vertices)
    }

    fun countPaths(hash: Hash, start: Vertex, end: Vertex, visited: MutableSet<Vertex>, cache: HashMap<Vertex,Int>): Int {
        if (cache.containsKey(start)) {
            return cache[start]!!
        }

        if (start == end) {
            return 1
        }
        visited.add(start)
        var pathCount = 0
        val neighbors = hash.vertices[start] ?: emptyList()
        for (neighbor in neighbors) {
            if (!visited.contains(neighbor)) {
                pathCount += countPaths(hash, neighbor, end, visited, cache)
            }
        }
        visited.remove(start)
        return pathCount
    }

    fun solution1(hash: Hash): Int {
        return countPaths(hash, Vertex("you"), Vertex("out"), mutableSetOf(), HashMap())
    }
}

fun main() {
    val input = Day11.readInput("./src/main/resources/day11/input1.txt")
    println(input)
    println()

    val result = Day11.solution1(input)
    println("Solution 1: $result")
    println()

}