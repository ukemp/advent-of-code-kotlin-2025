package util

class AdjacencyMatrix(private val coordinates: List<Coordinate3D>) {

    private val distances: Array<FloatArray>

    init {
        val size = coordinates.size
        distances = Array(size) { FloatArray(size) }
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (i == j) {
                    distances[i][j] = 0f
                } else {
                    distances[i][j] = coordinates[i].distanceTo(coordinates[j])
                }
            }
        }
    }

    fun sortedJunctions(): List<Junction> {
        val size = coordinates.size
        return buildList {
            for (i in 0 until size) {
                for (j in i + 1 until size) {
                    add(Junction(coordinates[i], coordinates[j], distances[i][j]))
                }
            }
        }.sorted()
    }

    fun getDistance(from: Coordinate3D, to: Coordinate3D): Float? {
        val fromIndex = coordinates.indexOf(from)
        val toIndex = coordinates.indexOf(to)
        if (fromIndex == -1 || toIndex == -1) {
            return null
        }
        return distances[fromIndex][toIndex]
    }

    fun getDistance(fromIndex: Int, toIndex: Int): Float {
        return distances[fromIndex][toIndex]
    }

    fun size(): Int {
        return coordinates.size
    }

    fun getCoordinate(index: Int): Coordinate3D {
        return coordinates[index]
    }
}

data class Junction(val c1: Coordinate3D, val c2: Coordinate3D, val distance: Float) : Comparable<Junction> {

    override fun compareTo(other: Junction): Int {
        return this.distance.compareTo(other.distance)
    }
}