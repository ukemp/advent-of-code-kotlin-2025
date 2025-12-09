package day09

import util.readLines
import kotlin.math.absoluteValue
import kotlin.time.measureTime

data class Tile(val x: Int, val y: Int) {

    fun areaOf(other: Tile): Long {
        return (((x - other.x).absoluteValue + 1).toLong() * ((y - other.y).absoluteValue + 1))
    }
}

fun main() {
    fun part1(input: List<String>): Long {
        val tiles = input.map { it.split(",").let { (x, y) -> Tile(x.toInt(), y.toInt()) } }
        val size = tiles.size
        val areas: Array<LongArray> = Array(size) { LongArray(size) }
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (i == j) {
                    areas[i][j] = 0L
                } else {
                    areas[i][j] = tiles[i].areaOf(tiles[j])
                }
            }
        }

        return buildList {
            for (i in 0 until size) {
                for (j in i + 1 until size) {
                    add(areas[i][j])
                }
            }
        }.maxOf { it }
    }

    fun part2(input: List<String>): Long {
        return input.size.toLong()
    }

    val testInput = readLines("Day09_test")
    check(part1(testInput).also { println("1. Test Result: $it") } == 50L)
    //check(part2(testInput).also {  println("2. Test Result: $it") } == 6L)

    val input = readLines("Day09")

    measureTime {
        println(part1(input))
    }.also { println("Solved part 1 in $it") }

    measureTime {
        println(part2(input))
    }.also { println("Solved part 2 in $it") }
}
