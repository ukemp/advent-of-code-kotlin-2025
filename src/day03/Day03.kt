package day03

import util.readLines
import kotlin.time.measureTime

fun maxJoltage(line: String, digits: Int): Long {
    return buildString {
        (digits - 1 downTo 0).toList().fold(0) { start, minSize ->
            val j1 = maxDigitOf(line, start, line.length - minSize)
            append(j1.second)
            j1.first + 1
        }
    }.toLong()
}

fun maxDigitOf(line: String, start: Int, end: Int): Pair<Int, Char> {
    var max = -1
    var index = -1
    for (i in start until end) {
        if (line[i].digitToInt() > max) {
            max = line[i].digitToInt()
            index = i
        }
    }
    return index to max.digitToChar()
}

fun main() {
    fun part1(input: List<String>): Long {
        return input.sumOf { line -> maxJoltage(line, 2) }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { line -> maxJoltage(line, 12) }
    }

    val testInput = readLines("Day03_test")
    check(part1(testInput).also { println("1. Test Result: $it") } == 357L)
    check(part2(testInput).also { println("2. Test Result: $it") } == 3121910778619L)

    val input = readLines("Day03")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
