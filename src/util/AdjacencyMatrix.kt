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

    /**
     * Returns a list of all junctions sorted from the shortest distance to the longest.
     */
    fun sortedJunctions(): List<Junction<T>> {
        val size = this.size
        return buildList {
            for (i in 0 until size) {
                for (j in i + 1 until size) {
                    add(Junction(measurables[i], measurables[j], distances[i][j]))
                }
            }
        }.sorted()
    }
}

/**
 * Encapsulates the distance between to measurables.
 *
 * @see AdjacencyMatrix.sortedJunctions
 */
data class Junction<T : Measurable<T>>(val c1: T, val c2: T, val distance: Double) : Comparable<Junction<T>> {

    override fun compareTo(other: Junction<T>): Int {
        return distance.compareTo(other.distance)
    }
}
