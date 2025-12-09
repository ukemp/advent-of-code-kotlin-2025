package util

class AdjacencyMatrix<T : Measurable<T>>(private val measurables: List<T>) {

    private val distances: Array<DoubleArray>

    init {
        val size = measurables.size
        distances = Array(size) { DoubleArray(size) }
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (i == j) {
                    distances[i][j] = 0.0
                } else {
                    distances[i][j] = measurables[i].distanceTo(measurables[j])
                }
            }
        }
    }

    val size: Int
        get() = measurables.size

    operator fun get(i: Int, j: Int): Double {
        return distances[i][j]
    }

    fun measurableAt(index: Int): T {
        return measurables[index]
    }

    fun distanceBetween(from: T, to: T): Double? {
        val fromIndex = measurables.indexOf(from)
        val toIndex = measurables.indexOf(to)
        if (fromIndex == -1 || toIndex == -1) {
            return null
        }
        return distances[fromIndex][toIndex]
    }
}

/**
 * Encapsulates the distance between to measurables.
 *
 * @see sortedJunctions
 */
data class Junction<T : Measurable<T>>(val c1: T, val c2: T, val distance: Double) : Comparable<Junction<T>> {

    override fun compareTo(other: Junction<T>): Int {
        return this.distance.compareTo(other.distance)
    }
}

/**
 * Returns a list of all junctions sorted from the shortest distance to the longest.
 */
fun <T : Measurable<T>> AdjacencyMatrix<T>.sortedJunctions(): List<Junction<T>> {
    // Implemented as an extension function to let Junction have the same T as the AdjacencyMatrix, otherwise Junction
    // must be an inner class on AdjacencyMatrix.
    val size = this.size
    return buildList<Junction<T>> {
        for (i in 0 until size) {
            for (j in i + 1 until size) {
                add(Junction(measurableAt(i), measurableAt(j), this@sortedJunctions[i, j]))
            }
        }
    }.sorted()
}