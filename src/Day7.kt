import java.lang.Math.abs

class Day7 : AocDay<Long>() {
    override val name: String
        get() = "Day7"
    override val task1Spec: Long?
        get() = 37
    override val task2Spec: Long?
        get() = 168

    override fun task1(input: List<String>): Long {
        val positions = input[0].split(",").map { it.toInt() }
        val minPosition = positions.minOf { it }
        val maxPosition = positions.maxOf { it }
        var minNeededFuel = Long.MAX_VALUE
        findAlignment@ for (i in minPosition..maxPosition) {
            var neededFuel = 0L
            for (pos in positions) {
                neededFuel += abs(pos - i)
                if (neededFuel >= minNeededFuel) {
                    continue@findAlignment
                }
            }
            minNeededFuel = neededFuel
        }
        return minNeededFuel
    }

    override fun task2(input: List<String>): Long {
        val positions = input[0].split(",").map { it.toInt() }
        val minPosition = positions.minOf { it }
        val maxPosition = positions.maxOf { it }
        var minNeededFuel = Long.MAX_VALUE
        findAlignment@ for (i in minPosition..maxPosition) {
            var neededFuel = 0L
            for (pos in positions) {
                neededFuel += (1..abs(pos - i)).sum()
                if (neededFuel >= minNeededFuel) {
                    continue@findAlignment
                }
            }
            minNeededFuel = neededFuel
        }
        return minNeededFuel
    }
}

fun main() {
    val task = Day7()
    task.run()
}