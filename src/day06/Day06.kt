package day06

import util.readLines
import kotlin.time.measureTime

val add: (Long, Long) -> Long = { a, b -> a + b }
val multiply: (Long, Long) -> Long = { a, b -> a * b }

data class Problem(val numbers: List<Long>, val operator: (Long, Long) -> Long) {

    fun solve(): Long {
        return numbers.reduce { acc, l -> operator(acc, l) }
    }
}

fun createProblemsPart1(input: List<String>): List<Problem> {
    val ops = input.last().trim().split("\\s+".toRegex())
        .map {
            when (it) {
                "+" -> add
                "*" -> multiply
                else -> throw IllegalArgumentException(it)
            }
        }

    val numbers = input.dropLast(1).map { line ->
        line.trim().split("\\s+".toRegex()).map { it.toLong() }
    }

    return List(numbers[0].size) { index ->
        val n = (0 until numbers.size).map { numbers[it][index] }
        Problem(n, ops[index])
    }
}

fun main() {
    fun part1(input: List<String>): Long {
        return createProblemsPart1(input).sumOf { it.solve() }
    }

    fun part2(input: List<String>): Long {
        return input.size.toLong()
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
