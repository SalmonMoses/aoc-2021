import java.util.*

class Day15 : AocDay<Long>() {
    override val name: String
        get() = "Day15"
    override val task1Spec: Long?
        get() = 40
    override val task2Spec: Long?
        get() = 315

    fun dijkstra(grid: Grid<Int>, from: GridPoint, to: GridPoint): Long {
        val frontier = PriorityQueue<Pair<GridPoint, Int>>(Comparator.comparingInt { it.second })
        val costs = mutableMapOf<GridPoint, Int>()

        frontier.add(Pair(from, 0))
        costs.set(from, 0)
        while (!frontier.isEmpty()) {
            val current = frontier.poll().first

            if (current == to) {
                break
            }

            grid.getNeighbors(current.first, current.second).forEach { next ->
                val newCost = costs[current]!! + grid[next.first, next.second]
                if (next !in costs || newCost < costs[next]!!) {
                    costs.set(next, newCost)
                    frontier.add(Pair(next, newCost))
                }
            }
        }

        return costs[to]!!.toLong()
    }

    fun incrementRow(row: List<Int>, times: Int): List<Int> {
        return row.map {
            var newValue = it + times
            if (newValue >= 10) {
                newValue -= 9
            }
            newValue
        }
    }

    fun incrementGrid(grid: Grid<Int>, times: Int): Grid<Int> {
        return Grid(grid.grid.map { row -> incrementRow(row, times) })
    }

    fun repeatMap(original: Grid<Int>, times: Int): Grid<Int> {
        val originalLists = original.grid.map { it.toMutableList() }.toMutableList()
        for (i in 1 until times) {
            originalLists.addAll(incrementGrid(original, i).grid.map { row -> row.toMutableList() })
        }
        for (row in originalLists) {
            val originalRow = row.toList()
            for (i in 1 until times) {
                row.addAll(incrementRow(originalRow, i))
            }
        }
        return Grid(originalLists)
    }

    override fun task1(input: List<String>): Long {
        val grid = Grid(input.map { row -> row.map { it.digitToInt() } })
        return dijkstra(grid, 0 to 0, grid.width - 1 to grid.height - 1)
    }

    override fun task2(input: List<String>): Long {
        val grid = Grid(input.map { row -> row.map { it.digitToInt() } })
        return dijkstra(repeatMap(grid, 5), 0 to 0, grid.width * 5 - 1 to grid.height * 5 - 1)
    }
}

fun main() {
    val task = Day15()
    task.run()
}