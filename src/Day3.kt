class Day3 : AocDay<UInt>() {
    override val name: String
        get() = "Day3"
    override val task1Spec: UInt?
        get() = 198u
    override val task2Spec: UInt?
        get() = 230u

    fun findCommonBit(input: List<String>, position: Int): Char {
        var zeros = 0
        var ones = 0
        input.forEach {
            if (it[position] == '0') zeros += 1
            else ones += 1
        }
        return if (ones >= zeros) '1' else '0'
    }

    override fun task1(input: List<String>): UInt {
        var gammaRate = ""
        var epsilon = ""
        for (i in 0 until input[0].length) {
            val commonBit = findCommonBit(input, i)
            gammaRate += commonBit
            epsilon += if (commonBit == '0') "1" else "0"
        }
        val binaryGamma: UInt = gammaRate.toUInt(2)
        val binaryEpsilon: UInt = epsilon.toUInt(2)
        return binaryGamma * binaryEpsilon
    }

    override fun task2(input: List<String>): UInt {
        var generatorCandidates = input
        var scrubberCandidates = input
        var generator: String? = null
        var scrubber: String? = null
        var position = 0
        while (true) {
            if (generator == null) {
                var zeros = 0
                var ones = 0
                generatorCandidates.forEach {
                    if (it[position] == '0') zeros += 1
                    else ones += 1
                }
                val commonBit = if (ones >= zeros) '1' else '0'
                generatorCandidates = generatorCandidates.filter { it[position] == commonBit }
                if (generatorCandidates.size == 1) generator = generatorCandidates[0]
            }
            if (scrubber == null) {
                var zeros = 0
                var ones = 0
                scrubberCandidates.forEach {
                    if (it[position] == '0') zeros += 1
                    else ones += 1
                }
                val lessCommonBit = if (zeros <= ones) '0' else '1'
                scrubberCandidates = scrubberCandidates.filter { it[position] == lessCommonBit }
                if (scrubberCandidates.size == 1) scrubber = scrubberCandidates[0]
            }
            if (scrubber != null && generator != null) break
            position++
        }
        val binaryGenerator: UInt = generator!!.toUInt(2)
        val binaryScrubber: UInt = scrubber!!.toUInt(2)
        return binaryGenerator * binaryScrubber
    }
}

fun main() {
    val task: AocDay<UInt> = Day3()
    task.run()
}