class TubesGrid(grid: Array<LongArray>) : Grid<Long>(grid.map { it.toList() }) {
    fun isLowpoint(x: Int, y: Int): Boolean {
        val adjacencies = getNeighborValues(x, y)
        val checkingPoint = this[x, y]
        return adjacencies.map { it > checkingPoint }.reduce { acc, curr -> acc && curr }
    }

    fun getBasinSize(x: Int, y: Int): Long {
        val basinPoints = mutableListOf(Pair(x, y))
        val checkQueue = ArrayDeque<Pair<Int, Int>>()
        val visitedPoints = mutableSetOf<Pair<Int, Int>>()
        checkQueue.add(Pair(x, y))
        while (checkQueue.isNotEmpty()) {
            val nextPoint = checkQueue.removeFirst()
            val nextPointValue = this[nextPoint.first, nextPoint.second]
            val adjacents = this.getNeighbors(nextPoint.first, nextPoint.second)
            for (point in adjacents) {
                if (visitedPoints.contains(point)) {
                    continue
                }
                val pointValue = this[point.first, point.second]
                if (pointValue != 9L && pointValue >= nextPointValue) {
                    basinPoints.add(point)
                    checkQueue.addFirst(point)
                }
            }
            visitedPoints.add(nextPoint)
        }
        return basinPoints.distinct().size.toLong()
    }
}

class Day9 : AocDay<Long>() {
    override val name: String
        get() = "Day9"
    override val task1Spec: Long?
        get() = 15
    override val task2Spec: Long?
        get() = 1134

    override fun task1(input: List<String>): Long {
        val gridArray = Array(input.size) { LongArray(input[0].length) { 0 } }
        for (i in 0 until input.size) {
            gridArray[i] = input[i].split("").filter { it.isNotBlank() }.map { it.toLong() }.toLongArray()
        }
        val grid = TubesGrid(gridArray)
        val lowPoints = mutableListOf<Long>()
        for (y in 0 until grid.height) {
            for (x in 0 until grid.width) {
                if (grid.isLowpoint(x, y)) {
                    lowPoints.add(grid[x, y])
                }
            }
        }
        return lowPoints.map { it + 1 }.sum()
    }

    override fun task2(input: List<String>): Long {
        val gridArray = Array(input.size) { LongArray(input[0].length) { 0 } }
        for (i in 0 until input.size) {
            gridArray[i] = input[i].split("").filter { it.isNotBlank() }.map { it.toLong() }.toLongArray()
        }
        val grid = TubesGrid(gridArray)
        val lowPoints = mutableListOf<Pair<Int, Int>>()
        for (y in 0 until grid.height) {
            for (x in 0 until grid.width) {
                if (grid.isLowpoint(x, y)) {
                    lowPoints.add(Pair(x, y))
                }
            }
        }
        return lowPoints.map { grid.getBasinSize(it.first, it.second) }
            .sortedDescending()
            .take(3)
            .reduce { acc, l -> acc * l }
    }
}

fun main() {
    val task = Day9()
    task.run()
}