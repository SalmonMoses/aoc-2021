import java.util.*

class Day15 : AocDay<Long>() {
    override val name: String
        get() = "Day15"
    override val task1Spec: Long?
        get() = 40
    override val task2Spec: Long?
        get() = 315

    fun manhattan(from: GridPoint, to: GridPoint): Int {
        return Math.abs(from.first - to.first) + Math.abs(from.second - to.second)
    }

    fun aStar(grid: Grid<Int>, from: GridPoint, to: GridPoint): Long {
        val frontier = PriorityQueue<Pair<GridPoint, Int>>(Comparator.comparingInt { it.second })
        val costs = mutableMapOf<GridPoint, Int>()

        frontier.add(Pair(from, 0))
        costs.set(from, 0)
        while (!frontier.isEmpty()) {
            val current = frontier.poll()

            if (current.first == to) {
                break
            }

            grid.getNeighbors(current.first.first, current.first.second).forEach { next ->
                val newCost = costs[current.first]!! + grid[next.first, next.second]
                if (next !in costs || newCost < costs[next]!!) {
                    costs.set(next, newCost)
                    val priority = newCost + manhattan(next, to)
                    frontier.add(Pair(next, priority))
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
        for (i in 1..4) {
            originalLists.addAll(incrementGrid(original, i).grid.map { row -> row.toMutableList() })
        }
        for (row in originalLists) {
            val originalRow = row.toList()
            for(i in 1..4) {
                row.addAll(incrementRow(originalRow, i))
            }
        }
        return Grid(originalLists)
    }

    override fun task1(input: List<String>): Long {
        val grid = Grid(input.map { row -> row.map { it.digitToInt() } })
        return aStar(grid, 0 to 0, grid.width - 1 to grid.height - 1)
    }

    override fun task2(input: List<String>): Long {
        val grid = Grid(input.map { row -> row.map { it.digitToInt() } })
        return aStar(repeatMap(grid, 5), 0 to 0, grid.width * 5 - 1 to grid.height * 5 - 1)
    }
}

fun main() {
    val task = Day15()
    task.run()
}