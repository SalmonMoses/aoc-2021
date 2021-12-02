fun main() {
    fun task1(input: List<String>): Int {
        var depth = 0
        var horizontal = 0
        input.map {
            it.split(" ")
        }.forEach {
            val direction = it[0]
            val quantity = it[1].toInt()
            when (direction) {
                "forward" -> horizontal += quantity
                "down" -> depth += quantity
                "up" -> depth -= quantity
            }
        }
        return depth * horizontal
    }

    fun task2(input: List<String>): Int {
        var depth = 0
        var horizontal = 0
        var aim = 0
        input.map {
            it.split(" ")
        }.forEach {
            val direction = it[0]
            val quantity = it[1].toInt()
            when (direction) {
                "forward" -> {
                    horizontal += quantity
                    depth += aim * quantity
                }
                "down" -> aim += quantity
                "up" -> aim -= quantity
            }
        }
        return depth * horizontal
    }

    val input = readInput("Day2")
    println(task1(input))
    println(task2(input))
}