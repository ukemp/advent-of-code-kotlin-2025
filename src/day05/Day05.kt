package day05

import util.readLines
import kotlin.time.measureTime

fun allRanges(input: List<String>): List<LongRange> {
    return buildList {
        for (line in input) {
            if (line.isEmpty()) break
            val ranges = line.split("-")
            add(ranges[0].toLong()..ranges[1].toLong())
        }
    }
}

val LongRange.count: Long
    get() = last - first + 1

val List<LongRange>.count: Long
    get() = sumOf { it.count }

fun main() {
    fun part1(input: List<String>): Long {
        val ranges = allRanges(input)

        return input
            .dropWhile { it.contains("-") || it.isEmpty() }
            .count { text ->
                val id = text.toLong()
                ranges.any { range -> id in range }
            }.toLong()
    }

    fun part2(input: List<String>): Long {
        return allRanges(input)
            .sortedBy { it.first }
            .fold(emptyList<LongRange>()) { acc, next ->
                val prev: LongRange = acc.lastOrNull() ?: return@fold listOf(next)

                when {
                    next.first > prev.last + 1 -> acc.plusElement(next)
                    next.last > prev.last -> acc.dropLast(1).plusElement(prev.first..next.last)
                    else -> acc
                }
            }
            .count
    }

    val testInput = readLines("Day05_test")
    check(part1(testInput).also { println("1. Test Result: $it") } == 3L)
    check(part2(testInput).also { println("2. Test Result: $it") } == 14L)

    val input = readLines("Day05")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
