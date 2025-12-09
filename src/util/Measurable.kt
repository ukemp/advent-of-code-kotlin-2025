package util

/**
 * Defines an interface for measuring the distance between two types.
 *
 * @see AdjacencyMatrix
 */
interface Measurable<T> {

    fun distanceTo(other: T): Float
}