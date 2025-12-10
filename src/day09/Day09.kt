package day09

import util.AdjacencyMatrix
import util.Measurable
import util.readLines
import kotlin.math.absoluteValue
import kotlin.time.measureTime

data class Tile(val x: Int, val y: Int) : Measurable<Tile> {

    override fun distanceTo(other: Tile): Double {
        return (((x - other.x).absoluteValue + 1).toLong() * ((y - other.y).absoluteValue + 1)).toDouble()
    }
}

fun main() {
    fun part1(input: List<String>): Long {
        val tiles = input.map { it.split(",").let { (x, y) -> Tile(x.toInt(), y.toInt()) } }
        val m = AdjacencyMatrix(tiles)

        return m.sortedJunctions().last().distance.toLong()
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
