package day04

import util.CharGrid
import util.Coordinate
import util.readLines
import kotlin.time.measureTime

fun CharGrid.canAccess(c: Coordinate) = this[c, ' '] == '@' && c.neighbors.count { this[it, ' '] == '@' } <= 3

fun main() {
    fun part1(input: List<String>): Long {
        val grid = CharGrid(input)
        return grid.coordinates().count { c -> grid.canAccess(c) }.toLong()
    }

    fun part2(input: List<String>): Long {
        var current = CharGrid(input)
        var sum = 0L
        while (true) {
            val next = current.copy()
            val count = current.coordinates().count { c ->
                if (current.canAccess(c)) {
                    next[c] = '.'
                    true
                } else {
                    false
                }
            }
            if (count == 0) break
            current = next
            sum += count
        }
        return sum
    }

    val testInput = readLines("Day04_test")
    check(part1(testInput).also { println("1. Test Result: $it") } == 13L)
    check(part2(testInput).also { println("2. Test Result: $it") } == 43L)

    val input = readLines("Day04")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
