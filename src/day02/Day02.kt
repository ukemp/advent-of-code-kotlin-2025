package day02

import util.readLines
import kotlin.time.measureTime

fun parseLine(line: String): List<Pair<Long, Long>> {
    return line.split(',').map { part ->
        part.substringBefore('-').toLong() to part.substringAfter('-').toLong()
    }
}

fun isInvalid1(productId: Long): Boolean {
    val text = productId.toString()
    for (i in 1..text.length / 2) {
        val sequence = text.subSequence(0, i)
        if (sequence.repeat(2) == text) {
            return true
        }
    }
    return false
}

fun isInvalid2(productId: Long): Boolean {
    val text = productId.toString()
    for (i in 1..text.length / 2) {
        val sequence = text.subSequence(0, i)
        var repeats = 2
        while (sequence.length * repeats <= text.length) {
            val candidate = sequence.repeat(repeats)
            if (candidate == text) {
                return true
            }
            repeats++
        }
    }
    return false
}

fun main() {
    fun part1(input: List<String>): Long {
        return parseLine(input[0]).sumOf { pair ->
            var count = 0L
            for (productId in pair.first..pair.second) {
                if (isInvalid1(productId)) {
                    count += productId
                }
            }
            count
        }
    }

    fun part2(input: List<String>): Long {
        return parseLine(input[0]).sumOf { pair ->
            var count = 0L
            for (productId in pair.first..pair.second) {
                if (isInvalid2(productId)) {
                    count += productId
                }
            }
            count
        }
    }

    val testInput = readLines("Day02_test")
    check(part1(testInput).also { println("1. Test Result: $it") } == 1227775554L)
    check(part2(testInput).also { println("2. Test Result: $it") } == 4174379265L)

    val input = readLines("Day02")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
