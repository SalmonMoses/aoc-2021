class Day13 : AocDay<Long>() {
    override val name: String
        get() = "Day13"
    override val task1Spec: Long?
        get() = 17
    override val task2Spec: Long?
        get() = null

    override fun task1(input: List<String>): Long {
        var points = mutableListOf<Pair<Long, Long>>()
        var line = 0
        var maxX = 0L
        var maxY = 0L
        while (input[line].isNotEmpty()) {
            val coords = input[line].split(",").filter { it.isNotEmpty() }.map { it.toLong() }
            val x = coords[0]
            val y = coords[1]
            points.add(Pair(x, y))
            if (x > maxX) {
                maxX = x
            }
            if (y > maxY) {
                maxY = y
            }
            ++line
        }
        ++line
        val foldDesc = input[line].substring(11)
        val coordinate = foldDesc.substring(2).toLong()
        when (foldDesc[0]) {
            'x' -> {
                points =
                    points.map { (x, y) -> Pair(if (x > coordinate) maxX - x else x, y) }.distinct().toMutableList()
                maxX = coordinate - 1
            }
            'y' -> {
                points =
                    points.map { (x, y) -> Pair(x, if (y > coordinate) maxY - y else y) }.distinct().toMutableList()
                maxY = coordinate - 1
            }
        }
        ++line
        return points.count().toLong()
    }

    override fun task2(input: List<String>): Long {
        var points = mutableListOf<Pair<Long, Long>>()
        var line = 0
        while (input[line].isNotEmpty()) {
            val coords = input[line].split(",").filter { it.isNotEmpty() }.map { it.toLong() }
            val x = coords[0]
            val y = coords[1]
            points.add(Pair(x, y))
            ++line
        }
        var maxX = points.maxOf { it.first }
        var maxY = points.maxOf { it.second }
        ++line
        while (line < input.size && input[line].isNotEmpty()) {
            val foldDesc = input[line].substring(11)
            val axis = foldDesc[0]
            val coordinate = foldDesc.substring(2).toLong()
            when (axis) {
                'x' -> {
                    points =
                        points.map { (x, y) -> Pair(if (x > coordinate) coordinate - (x - coordinate) else x, y) }.distinct().toMutableList()
                    maxX = coordinate - 1
                }
                'y' -> {
                    points =
                        points.map { (x, y) -> Pair(x, if (y > coordinate) coordinate - (y - coordinate) else y) }.distinct().toMutableList()
                    maxY = coordinate - 1
                }
            }
            ++line
        }
        for (y in 0..maxY) {
            for (x in 0..maxX) {
                if (Pair(x, y) in points) {
                    print("#")
                } else {
                    print(" ")
                }
            }
            println()
        }
        return points.count().toLong()
    }
}

fun main() {
    val task = Day13()
    task.run()
}