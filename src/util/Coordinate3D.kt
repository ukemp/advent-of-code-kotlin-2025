package util

import kotlin.math.sqrt

@Suppress("NOTHING_TO_INLINE")
inline fun Coordinate3D(str: String, delimiter: Char = ','): Coordinate3D {
    return str.split(delimiter)
        .let { (x, y, z) -> Coordinate3D(x.toInt(), y.toInt(), z.toInt()) }
}

class Coordinate3D(val x: Int, val y: Int, val z: Int) : Measurable<Coordinate3D> {

    operator fun component1(): Int = x

    operator fun component2(): Int = y

    operator fun component3(): Int = z

    override fun distanceTo(other: Coordinate3D): Double {
        val sq = (other.x - this.x).let { it.toLong() * it } +
                (other.y - this.y).let { it.toLong() * it } +
                (other.z - this.z).let { it.toLong() * it }
        return sqrt(sq.toDouble())
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Coordinate3D) return false

        return this.x == other.x && this.y == other.y && this.z == other.z
    }

    override fun hashCode(): Int {
        return x xor y xor z
    }

    override fun toString(): String {
        return "[$x,$y,$z]"
    }

    companion object {
        val ORIGIN = Coordinate3D(0, 0, 0)
    }
}