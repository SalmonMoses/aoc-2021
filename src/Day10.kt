class Day10 : AocDay<Long>() {
    override val name: String
        get() = "Day10"
    override val task1Spec: Long?
        get() = 26397
    override val task2Spec: Long?
        get() = null

    val openingChars = listOf('(', '[', '{', '<')
    val closingChars = listOf(')', ']', '}', '>')
    val openClosePairs = openingChars.zip(closingChars).toMap()
    val closeScores = mapOf(Pair(')', 3), Pair(']', 57), Pair('}', 1197), Pair('>', 25137))
    val closeAutocompleteScores = closingChars.zip(1..4).toMap()

    fun validateLine(line: String): Boolean {
        val stack = ArrayDeque<Char>()
        for (char in line) {
            if (char in openingChars) {
                stack.addFirst(char)
            } else if (char in closingChars) {
                val lastOpeningChar = stack.removeFirst()
                if (openClosePairs[lastOpeningChar] != char) {
                    return false
                }
            }
        }
        return true
    }

    fun parseLine(line: String): Long {
        var score = 0L
        val stack = ArrayDeque<Char>()
        for (char in line) {
            if (char in openingChars) {
                stack.addFirst(char)
            } else if (char in closingChars) {
                val lastOpeningChar = stack.removeFirst()
                if (openClosePairs[lastOpeningChar] != char) {
                    score += closeScores[char]!!
                }
            }
        }
        return score
    }

    fun autocompleteLine(line: String): Long {
        var score = 0L
        val stack = ArrayDeque<Char>()
        for (char in line) {
            if (char in openingChars) {
                stack.addFirst(char)
            } else if (char in closingChars) {
                val lastOpeningChar = stack.removeFirst()
            }
        }
        while (stack.isNotEmpty()) {
            val lastOpeningChar = stack.removeFirst()
            val closePair = openClosePairs[lastOpeningChar]!!
            score *= 5
            score += closeAutocompleteScores[closePair]!!
        }
        return score
    }

    override fun task1(input: List<String>): Long {
        return input.map { parseLine(it) }.sum()
    }

    override fun task2(input: List<String>): Long {
        val scores = input.filter { validateLine(it) }.map { autocompleteLine(it) }.sorted()
        return scores[(scores.size + 1) / 2 - 1]
    }
}

fun main() {
    val task = Day10()
    task.run()
}