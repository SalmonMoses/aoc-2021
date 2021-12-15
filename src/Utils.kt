import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/main/resources", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

typealias GridPoint = Pair<Int, Int>

open class Grid<T>(val grid: List<List<T>>) {
    open operator fun get(x: Int, y: Int): T = grid[y][x]

    val height = grid.size
    val width = grid[0].size

    fun getNeighbors(x: Int, y: Int): List<GridPoint> {
        val neighbors = mutableListOf<GridPoint>()
        if ((x + 1) in 0 until width) {
            neighbors.add(GridPoint(x + 1, y))
        }
        if ((x - 1) in 0 until width) {
            neighbors.add(GridPoint(x - 1, y))
        }
        if ((y + 1) in 0 until height) {
            neighbors.add(GridPoint(x, y + 1))
        }
        if ((y - 1) in 0 until height) {
            neighbors.add(GridPoint(x, y - 1))
        }
        return neighbors
    }

    fun getNeighborValues(x: Int, y: Int): List<T> = getNeighbors(x, y).map { this[it.first, it.second] }

    fun getNeighborsDiagonal(x: Int, y: Int): List<GridPoint> {
        val neighbors = mutableListOf<GridPoint>()
        (-1..1).forEach { dy ->
            (-1..1).forEach { dx ->
                if (dx != 0 || dy != 0) {
                    val newX = x + dx
                    val newY = y + dy
                    if (newX in (0..9) && newY in (0..9)) {
                        neighbors.add(GridPoint(newX, newY))
                    }
                }
            }
        }
        return neighbors
    }

    fun getNeighborValuesDiagonal(x: Int, y: Int): List<T> =
        getNeighborsDiagonal(x, y).map { this[it.first, it.second] }
}

open class MutableGrid<T>(val grid: MutableList<MutableList<T>>) {
    open operator fun get(x: Int, y: Int): T = grid[y][x]
    open operator fun set(x: Int, y: Int, value: T) {
        grid[y][x] = value
    }

    val height = grid.size
    val width = grid[0].size

    fun getNeighbors(x: Int, y: Int): List<GridPoint> {
        val neighbors = mutableListOf<GridPoint>()
        if ((x + 1) in 0 until width) {
            neighbors.add(GridPoint(x + 1, y))
        }
        if ((x - 1) in 0 until width) {
            neighbors.add(GridPoint(x - 1, y))
        }
        if ((y + 1) in 0 until height) {
            neighbors.add(GridPoint(x, y + 1))
        }
        if ((y - 1) in 0 until height) {
            neighbors.add(GridPoint(x, y - 1))
        }
        return neighbors
    }

    fun getNeighborValues(x: Int, y: Int): List<T> = getNeighbors(x, y).map { this[it.first, it.second] }

    fun getNeighborsDiagonal(x: Int, y: Int): List<GridPoint> {
        val neighbors = mutableListOf<GridPoint>()
        (-1..1).forEach { dy ->
            (-1..1).forEach { dx ->
                if (dx != 0 || dy != 0) {
                    val newX = x + dx
                    val newY = y + dy
                    if (newX in (0..9) && newY in (0..9)) {
                        neighbors.add(GridPoint(newX, newY))
                    }
                }
            }
        }
        return neighbors
    }

    fun getNeighborValuesDiagonal(x: Int, y: Int): List<T> =
        getNeighborsDiagonal(x, y).map { this[it.first, it.second] }
}
