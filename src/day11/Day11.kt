package day11

import util.readLines
import kotlin.time.measureTime

fun depthFirstSearch(
    nodes: Map<String, List<String>>,
    currentNode: String,
    currentPath: MutableList<String>,
    results: MutableList<List<String>>
): List<List<String>> {
    nodes[currentNode]!!.filter { child -> child !in currentPath }.forEach { child ->
        if (child == "out") {
            results.add(currentPath)
        } else {
            currentPath.add(child)
            depthFirstSearch(nodes, child, currentPath, results)
        }
    }
    currentPath.remove(currentNode)

    return results
}

fun main() {
    fun part1(input: List<String>): Long {
        val nodes = input.associate { line ->
            line.split(":").let { (k, v) -> k.trim() to v.trim().split(" ").toList() }
        }
        val results = depthFirstSearch(nodes, "you", mutableListOf(), mutableListOf())

        return results.size.toLong()
    }

    fun part2(input: List<String>): Long {
        return input.size.toLong()
    }

    val testInput = readLines("Day11_test")
    check(part1(testInput).also { println("1. Test Result: $it") } == 5L)
    //check(part2(testInput).also {  println("2. Test Result: $it") } == 6L)

    val input = readLines("Day11")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
