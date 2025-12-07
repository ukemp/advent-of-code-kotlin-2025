package day07

import util.CharGrid
import util.Coordinate
import util.Direction.*
import util.readLines
import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Long {
        val grid = CharGrid(input)
        val start = grid.indexOf('S')
        grid[start + DOWN] = '|'

        val rows = grid.verticalIndices.drop(1)
        for (y in rows) {
            for (x in grid.horizontalIndices) {
                val c = Coordinate(x, y)
                if (grid[c] == '|') {
                    if (grid[c + DOWN] == '.') {
                        grid[c + DOWN] = '|'
                    } else if (grid[c + DOWN] == '^') {
                        grid[c + LEFT + DOWN] = '|'
                        grid[c + RIGHT + DOWN] = '|'
                    }
                }
            }
        }

        return grid.filter { position, c ->
            c == '^' && grid[position + UP] == '|'
        }.toList().size.toLong()
    }

    fun part2(input: List<String>): Long {
        return input.size.toLong()
    }

    val testInput = readLines("Day07_test")
    check(part1(testInput).also { println("1. Test Result: $it") } == 21L)
    //check(part2(testInput).also {  println("2. Test Result: $it") } == 6L)

    val input = readLines("Day07")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
