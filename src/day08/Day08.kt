package day08

import util.*
import kotlin.time.measureTime

class Circuit(vararg boxes: Coordinate3D) {

    private val boxes = mutableSetOf(*boxes)

    val size: Int
        get() = boxes.size

    fun add(junction: Junction<Coordinate3D>) {
        boxes.add(junction.c1)
        boxes.add(junction.c2)
    }

    fun matches(junction: Junction<Coordinate3D>): Int {
        var count = 0
        if (boxes.contains(junction.c1)) count++
        if (boxes.contains(junction.c2)) count++
        return count
    }

    fun join(other: Circuit) {
        check(boxes.addAll(other.boxes))
    }

    override fun toString(): String {
        return "$boxes"
    }
}

fun main() {
    fun part1(input: List<String>, max: Int): Long {
        val m = AdjacencyMatrix(input.map { Coordinate3D(it) })
        val junctions = m.sortedJunctions()
        val circuits = mutableListOf<Circuit>()

        for (junction in junctions.take(max)) {
            val matches = circuits.filter { circuit -> circuit.matches(junction) > 0 }
            check(matches.size < 3)

            if (matches.isEmpty()) {
                circuits.add(Circuit(junction.c1, junction.c2))
            } else if (matches.size == 1) {
                // This check is not really required, it's the "nothing happens" part of the puzzle:
                if (matches[0].matches(junction) == 1) {
                    matches[0].add(junction)
                }
            } else if (matches.size == 2) {
                matches[0].join(matches[1])
                check(circuits.remove(matches[1]))
            }
        }

        val sorted = circuits.sortedWith { p0, p1 -> p1.size.compareTo(p0.size) }
        return sorted.take(3).fold(1L) { next, junctions -> next * junctions.size }
    }

    fun part2(input: List<String>): Long {
        val all = input.map { Coordinate3D(it) }
        val junctions = AdjacencyMatrix(all).sortedJunctions()
        val circuits = all.map { Circuit(it) }.toMutableList()

        for (junction in junctions) {
            val matches = circuits.filter { circuit -> circuit.matches(junction) > 0 }
            check(matches.isNotEmpty() && matches.size < 3)

            if (matches.size == 1) {
                matches[0].add(junction)
            } else if (matches.size == 2) {
                matches[0].join(matches[1])
                check(circuits.remove(matches[1]))

                if (circuits.size == 1) {
                    return junction.c1.x * junction.c2.x.toLong()
                }
            }
        }
        return -1
    }

    val testInput = readLines("Day08_test")
    check(part1(testInput, 10).also { println("1. Test Result: $it") } == 40L)
    check(part2(testInput).also { println("2. Test Result: $it") } == 25272L)

    val input = readLines("Day08")

    measureTime {
        println(part1(input, 1000))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
