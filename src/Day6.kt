class Day6 : AocDay<Long>() {
    override val name: String
        get() = "Day6"
    override val task1Spec: Long?
        get() = 5934
    override val task2Spec: Long?
        get() = 26984457539

    private fun loop(population: LongArray, cycles: Int): Long {
        var fishSchool = population
        for (i in 0 until cycles) {
            val newFish = fishSchool[0]
            val newFishSchool = LongArray(9) { 0 }
            for (days in 0..7) {
                newFishSchool[days] = fishSchool[days + 1]
            }
            newFishSchool[6] += newFish
            newFishSchool[8] = newFish
            fishSchool = newFishSchool
        }
        return fishSchool.sum()
    }

    override fun task1(input: List<String>): Long {
        val initialPopulation = input[0].split(",").map { it.toInt() }
        val initialSchool = LongArray(9) { 0 }
        for (fish in initialPopulation) {
            initialSchool[fish] = initialSchool[fish] + 1
        }
        return loop(initialSchool, 80)
    }

    override fun task2(input: List<String>): Long {
        val initialPopulation = input[0].split(",").map { it.toInt() }
        val initialSchool = LongArray(9) { 0 }
        for (fish in initialPopulation) {
            initialSchool[fish] = initialSchool[fish] + 1
        }
        return loop(initialSchool, 256)
    }
}

fun main() {
    val task = Day6()
    task.run()
}