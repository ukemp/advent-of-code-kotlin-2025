package day01

import util.readLines
import kotlin.math.absoluteValue
import kotlin.time.measureTime

fun parseLine(line: String): Int {
    return line.substring(1).toInt() * if (line[0] == 'L') -1 else 1
}

fun main() {

    fun part1(input: List<String>): Long {
        var dial = 50
        return input.count { line ->
            dial += parseLine(line) % 100
            if (dial < 0) {
                dial = 100 - dial.absoluteValue
            } else if (dial >= 100) {
                dial -= 100
            }
            dial == 0
        }.toLong()
    }

    fun part2(input: List<String>): Long {
        var dial = 50
        return input.sumOf { line ->
            val wasZero = dial == 0
            val value = parseLine(line)
            dial += value % 100
            var count = if (value.absoluteValue >= 100) (value / 100).absoluteValue else 0

            count += if (dial < 0) {
                dial = 100 - dial.absoluteValue
                if (wasZero) 0 else 1
            } else if (dial >= 100) {
                dial -= 100
                1
            } else {
                if (dial == 0) 1 else 0
            }
            count
        }.toLong()
    }


    val testInput = readLines("Day01_test")
    check(part1(testInput).also { println("1. Test Result: $it") } == 3L)
    check(part2(testInput).also { println("2. Test Result: $it") } == 6L)

    val input = readLines("Day01")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
