package day06

import util.CharGrid
import util.Coordinate
import util.readLines
import kotlin.time.measureTime

val add: (Long, Long) -> Long = { a, b -> a + b }
val multiply: (Long, Long) -> Long = { a, b -> a * b }

fun Char.toOperator(): (Long, Long) -> Long {
    return when (this) {
        '+' -> add
        '*' -> multiply
        else -> throw IllegalArgumentException("$this")
    }
}

data class Problem(val numbers: List<Long>, val operator: (Long, Long) -> Long) {

    fun solve(): Long {
        return numbers.reduce { acc, l -> operator(acc, l) }
    }
}

fun createProblemsPart1(input: List<String>): List<Problem> {
    val ops = input.last().trim()
        .split("\\s+".toRegex())
        .map { it[0].toOperator() }

    val numbers = input.dropLast(1).map { line ->
        line.trim().split("\\s+".toRegex()).map { it.toLong() }
    }

    return List(numbers[0].size) { index ->
        val nums = (0 until numbers.size).map { numbers[it][index] }
        Problem(nums, ops[index])
    }
}

fun createProblemsPart2(input: List<String>): List<Problem> {
    val ops = input.last().trim()
    val grid = CharGrid(input.dropLast(1))

    return buildList {
        ops.flatMapIndexed { index, ch ->
            when (ch) {
                '+' -> listOf(index to add)
                '*' -> listOf(index to multiply)
                else -> emptyList()
            }
        }.windowed(2, 1, true) { pairs ->
            val start = pairs.first().first
            val end = if (pairs.size == 2) pairs[1].first - 2 else grid.width
            val op = pairs.first().second
            val numbers = buildList {
                for (x in start..end) {
                    val num = grid.wordTo(Coordinate(x, 0), Coordinate(0, grid.height - 1)).trim()
                    if (num.isNotEmpty()) {
                        add(num.toLong())
                    }
                }
            }
            add(Problem(numbers, op))
        }
    }
}

fun main() {
    fun part1(input: List<String>): Long {
        return createProblemsPart1(input).sumOf { it.solve() }
    }

    fun part2(input: List<String>): Long {
        return createProblemsPart2(input).sumOf { it.solve() }
    }

    val testInput = readLines("Day06_test")
    check(part1(testInput).also { println("1. Test Result: $it") } == 4277556L)
    check(part2(testInput).also { println("2. Test Result: $it") } == 3263827L)

    val input = readLines("Day06")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
