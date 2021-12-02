fun main() {
    fun part1(input: List<String>): Int {
        val numbers = input.map { it.toInt() }
        var result = 0
        for (i in 1 until numbers.size) {
            if (numbers[i] > numbers[i - 1]) {
                result += 1
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        val numbers = input.map { it.toInt() }.windowed(size = 3).map { it.sum() }
        var result = 0
        for (i in 1 until numbers.size) {
            if (numbers[i] > numbers[i - 1]) {
                result += 1
            }
        }
        return result
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
