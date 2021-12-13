class Octopus(var energy: Int) {
    fun charge(): Boolean {
        energy += 1
        return energy == 10
    }
}

class OctopusGrid(grid: List<List<Octopus>>) : Grid<Octopus>(grid) {
    fun chargeAt(x: Int, y: Int): Long {
        var flashes = 0L
        if (grid[y][x].charge()) {
            flashes += 1
            (-1..1).forEach { dy ->
                (-1..1).forEach { dx ->
                    val newX = x + dx
                    val newY = y + dy
                    if (newX in (0..9) && newY in (0..9)) {
                        flashes += chargeAt(x + dx, y + dy)
                    }
                }
            }
        }
        return flashes
    }

    fun reset() {
        (0..9).forEach { y ->
            (0..9).forEach { x ->
                if (grid[y][x].energy >= 10) {
                    grid[y][x].energy = 0
                }
            }
        }
    }
}

class Day11 : AocDay<Long>() {
    override val name: String
        get() = "Day11"
    override val task1Spec: Long?
        get() = 1656
    override val task2Spec: Long?
        get() = 195

    override fun task1(input: List<String>): Long {
        val octopuses = Array(10) { IntArray(10) { 0 } }
        for (y in 0..9) {
            for (x in 0..9) {
                octopuses[y][x] = input[y][x].digitToInt()
            }
        }
        val grid = OctopusGrid(octopuses.map { row ->
            row.map { Octopus(it) }
        })
        var flashes = 0L
        (0..99).forEach {
            for (y in 0..9) {
                for (x in 0..9) {
                    flashes += grid.chargeAt(x, y)
                }
            }
            grid.reset()
        }
        return flashes
    }

    override fun task2(input: List<String>): Long {
        val octopuses = Array(10) { IntArray(10) { 0 } }
        for (y in 0..9) {
            for (x in 0..9) {
                octopuses[y][x] = input[y][x].digitToInt()
            }
        }
        val grid = OctopusGrid(octopuses.map { row ->
            row.map { Octopus(it) }
        })
        var turns = 0L
        while (true) {
            turns++
            var flashes = 0L
            for (y in 0..9) {
                for (x in 0..9) {
                    flashes += grid.chargeAt(x, y)
                }
            }
            if (flashes == 100L) {
                return turns
            }
            grid.reset()
        }
    }
}

fun main() {
    val task = Day11()
    task.run()
}