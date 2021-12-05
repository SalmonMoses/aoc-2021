import java.lang.Math.abs

class Day5 : AocDay<Int>() {
    override val name: String
        get() = "Day5"
    override val task1Spec: Int?
        get() = 5
    override val task2Spec: Int?
        get() = 12

    data class Point(val x: Int, val y: Int)

    fun stringToPoint(str: String): Point {
        val coords = str.split(",")
        return Point(coords[0].toInt(), coords[1].toInt())
    }

    override fun task1(input: List<String>): Int {
        val lines: MutableList<Pair<Point, Point>> = mutableListOf()
        var highestX = 0
        var highestY = 0
        input.forEach {
            val points = it.split(" -> ")
            val from = stringToPoint(points[0])
            val to = stringToPoint(points[1])
            if (from.x > highestX) {
                highestX = from.x
            }
            if (to.x > highestX) {
                highestX = to.x
            }
            if (from.y > highestY) {
                highestY = from.y
            }
            if (to.y > highestY) {
                highestY = to.y
            }
            if (from.x == to.x || from.y == to.y) {
                lines.add(Pair(from, to))
            }
        }
        val linesMap = Array(highestY + 1) { Array<Int>(highestX + 1, { 0 }) }
        lines.forEach {
            val from = it.first
            val to = it.second
            if (from.x == to.x) {
                val startY = minOf(from.y, to.y)
                val endY = maxOf(from.y, to.y)
                val x = from.x
                for (y in startY..endY) {
                    val row = linesMap[y]
                    linesMap[y].set(x, row[x] + 1)
                }
            } else if (from.y == to.y) {
                val startX = minOf(from.x, to.x)
                val endX = maxOf(from.x, to.x)
                val y = from.y
                for (x in startX..endX) {
                    val row = linesMap[y]
                    linesMap[y].set(x, row[x] + 1)
                }
            }
        }
        return linesMap.flatten().filter { it > 1 }.count()
    }

    override fun task2(input: List<String>): Int {
        val lines: MutableList<Pair<Point, Point>> = mutableListOf()
        var highestX = 0
        var highestY = 0
        input.forEach {
            val points = it.split(" -> ")
            val from = stringToPoint(points[0])
            val to = stringToPoint(points[1])
            if (from.x > highestX) {
                highestX = from.x
            }
            if (to.x > highestX) {
                highestX = to.x
            }
            if (from.y > highestY) {
                highestY = from.y
            }
            if (to.y > highestY) {
                highestY = to.y
            }
            lines.add(Pair(from, to))
        }
        val linesMap = Array(highestY + 1) { Array<Int>(highestX + 1, { 0 }) }
        lines.forEach {
            val from = it.first
            val to = it.second
            if (from.x == to.x) {
                val startY = minOf(from.y, to.y)
                val endY = maxOf(from.y, to.y)
                val x = from.x
                for (y in startY..endY) {
                    val row = linesMap[y]
                    linesMap[y].set(x, row[x] + 1)
                }
            } else if (from.y == to.y) {
                val startX = minOf(from.x, to.x)
                val endX = maxOf(from.x, to.x)
                val y = from.y
                for (x in startX..endX) {
                    val row = linesMap[y]
                    linesMap[y].set(x, row[x] + 1)
                }
            } else {
                val dx = if (from.x < to.x) 1 else -1
                val dy = if (from.y < to.y) 1 else -1
                val distance = maxOf(abs(from.x - to.x), abs(from.y - to.y))
                var x = from.x
                var y = from.y
                for (i in 0..distance) {
                    val row = linesMap[y]
                    linesMap[y].set(x, row[x] + 1)
                    x += dx
                    y += dy
                }
            }
        }
        linesMap.forEach {
            it.forEach { print(if (it > 0) it else '.') }
            println("")
        }
        return linesMap.flatten().filter { it > 1 }.count()
    }
}

fun main() {
    val task: AocDay<Int> = Day5()
    task.run()
}