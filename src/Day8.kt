data class Entry(val patterns: List<String>, val output: List<String>) {
    companion object {
        fun fromString(desc: String): Entry {
            val parts = desc.split(" | ")
            val patterns = parts[0].split(" ")
            val output = parts[1].split(" ")
            return Entry(patterns, output)
        }
    }
}

class Alphabet {
    val symbols = 'a'..'g'
    val correctMap = symbols.map { it to (null as Char?) }.toMap().toMutableMap()
    val candidatesMap = symbols.map { it to symbols.toSet() }.toMap().toMutableMap()

    fun addGuess(symbol: Char, candidates: Set<Char>) {
        val actualCandidates = candidates.minus(getGuessedSymbols())
        if (actualCandidates.isEmpty()) return
        candidatesMap[symbol] = candidatesMap[symbol]!! intersect actualCandidates
        if (candidatesMap[symbol]!!.size == 1) {
            correctMap[symbol] = candidatesMap[symbol]!!.toList()[0]
        }
    }

    fun getGuessedSymbols(): Set<Char> {
        return correctMap.values.filter { it != null }.map { it!! }.toSet()
    }
}

class Day8 : AocDay<Long>() {
    override val name: String
        get() = "Day8"
    override val task1Spec: Long?
        get() = 26
    override val task2Spec: Long?
        get() = 61229

    override fun task1(input: List<String>): Long {
        return input.map { Entry.fromString(it) }.map { entry ->
            entry.output.filter { it.length in listOf(2, 4, 3, 7) }.count().toLong()
        }.sum()
    }

    override fun task2(input: List<String>): Long {
        val numbers = mapOf(
            0 to setOf('a', 'b', 'c', 'e', 'f', 'g'),
            1 to setOf('c', 'f'),
            2 to setOf('a', 'c', 'd', 'e', 'g'),
            3 to setOf('a', 'c', 'd', 'f', 'g'),
            4 to setOf('b', 'c', 'd', 'f'),
            5 to setOf('a', 'b', 'd', 'f', 'g'),
            6 to setOf('a', 'b', 'd', 'e', 'f', 'g'),
            7 to setOf('a', 'c', 'f'),
            8 to ('a'..'g').toSet(),
            9 to setOf('a', 'b', 'c', 'd', 'f', 'g')
        )
        val numbersWithFive = numbers.filter { it.value.size == 5 }.values.fold(setOf<Char>()) { acc, cur -> acc union cur }
        val numbersWithSix = numbers.filter { it.value.size == 6 }.values.fold(setOf<Char>()) { acc, cur -> acc union cur }
        return input.map { Entry.fromString(it) }.map { entry ->
            val alphabet = Alphabet()
            val one = entry.patterns.filter { it.length == 2 }[0]
            one.forEach { alphabet.addGuess(it, numbers[1]!!) }
            val four = entry.patterns.filter { it.length == 4 }[0]
            four.forEach { alphabet.addGuess(it, numbers[4]!!) }
            val seven = entry.patterns.filter { it.length == 3 }[0]
            seven.forEach { alphabet.addGuess(it, numbers[7]!!) }
            val eight = entry.patterns.filter { it.length == 7 }[0]
            eight.forEach { alphabet.addGuess(it, numbers[8]!!) }
            val patternWithFiveSymbols = entry.patterns.filter { it.length == 5 }
            patternWithFiveSymbols.forEach { pattern ->
                pattern.forEach { alphabet.addGuess(it, numbersWithFive) }
            }
            val patternWithSixSymbols = entry.patterns.filter { it.length == 6 }
            patternWithSixSymbols.forEach { pattern ->
                pattern.forEach { alphabet.addGuess(it, numbersWithSix) }
            }
            check(alphabet.correctMap.filter { it.value != null }.size == 7, { "Лох ты, а не криптоаналитик" })
            0L
        }.sum()
    }
}

fun main() {
    val task = Day8()
    task.run()
}