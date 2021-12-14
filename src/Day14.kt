import kotlin.math.abs

class Day14 : AocDay<Long>() {
    override val name: String
        get() = "Day14"
    override val task1Spec: Long?
        get() = 1588
    override val task2Spec: Long?
        get() = 2188189693529

    override fun task1(input: List<String>): Long {
        var template = input[0]
        val rules = mutableMapOf<String, Char>()
        for (i in 2 until input.size) {
            val rule = input[i].split(" -> ")
            rules[rule[0]] = rule[1][0]
        }
        for (i in 0..9) {
            var char = 0
            while (true) {
                val currentChar = template[char]
                val nextChar = template[char + 1]
                val maybePair = "$currentChar$nextChar"
                if (maybePair in rules) {
                    val element = rules[maybePair]
                    template = StringBuilder(template).insert(char + 1, element).toString()
                    ++char
                }
                ++char
                if (char >= template.length - 1) break
            }
        }
        val elementsByFrequency =
            template.map { char -> char to template.filter { char == it }.count() }.sortedBy { it.second }
                .map { it.second }
        return (elementsByFrequency.last() - elementsByFrequency.first()).toLong()
    }

    override fun task2(input: List<String>): Long {
        var template = input[0]
        val rules = mutableMapOf<String, Char>()
        for (i in 2 until input.size) {
            val rule = input[i].split(" -> ")
            rules[rule[0]] = rule[1][0]
        }
        var patterns = mutableMapOf<String, Long>()
        patterns.putAll(
            template.windowed(2).map { pattern -> pattern to Regex(pattern).findAll(template).count().toLong() })
        for (i in 0..39) {
            val newPatterns = patterns.toMutableMap()
            for (pattern in patterns.iterator()) {
                if (pattern.key in rules) {
                    newPatterns.compute(pattern.key) { p, c -> c!! - pattern.value }
                    val firstChar = pattern.key[0]
                    val secondChar = pattern.key[1]
                    val element = rules[pattern.key]
                    newPatterns.compute("$firstChar$element") { p, c -> if (c != null) c + pattern.value else pattern.value }
                    newPatterns.compute("$element$secondChar") { p, c -> if (c != null) c + pattern.value else pattern.value }
                }
            }
            patterns = newPatterns
        }
        val elementsByFrequency = mutableMapOf<Char, Long>()
        for ((pattern, count) in patterns) {
            elementsByFrequency.compute(pattern[0]) { _, frequency -> if (frequency != null) frequency + count / 2 else count / 2 }
            elementsByFrequency.compute(pattern[1]) { _, frequency -> if (frequency != null) frequency + count / 2 else count / 2 }
        }
        val elementsListByFrequency = elementsByFrequency.values.sortedBy { it }
        return elementsListByFrequency.last() - elementsListByFrequency.first()
    }
}

fun main() {
    val task = Day14()
    task.run()
}