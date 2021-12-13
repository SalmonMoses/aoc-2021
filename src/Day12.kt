class CaveGraph {
    private val connections = mutableMapOf<String, MutableList<String>>()

    fun addConnectionPair(a: String, b: String) {
        addConnection(a, b)
        addConnection(b, a)
    }

    fun addConnection(from: String, to: String) {
        if (from in connections) {
            connections[from]!!.add(to)
        } else {
            connections[from] = mutableListOf(to)
        }
    }

    fun findPaths(from: String, to: String): List<List<String>>? {
        return findPathsImpl(from, to, listOf(), listOf(), setOf())
    }

    fun findPathsImpl(
        from: String,
        to: String,
        currentPath: List<String>,
        foundPaths: List<List<String>>,
        visitedSmallCaves: Set<String>
    ): List<List<String>>? {
        if (from == to) {
            return listOf(currentPath + from)
        }
        if (from in visitedSmallCaves) {
            return null
        }
        val newCurrentPath = currentPath + from
        val newVisitedSmallCaves = if (from[0].isLowerCase()) visitedSmallCaves + from else visitedSmallCaves
        val candidatesForNextPoint = connections[from] ?: listOf()
        val foundPathsFromHere = mutableListOf<List<String>>()
        for (nextPoint in candidatesForNextPoint) {
            val newPaths =
                findPathsImpl(nextPoint, to, newCurrentPath, foundPaths + foundPathsFromHere, newVisitedSmallCaves)
            if (newPaths != null) {
                foundPathsFromHere += newPaths
            }
        }
        return foundPathsFromHere
    }

    fun findPathsTask2(from: String, to: String): List<List<String>>? {
        return findPathsTask2Impl(from, to, listOf(), listOf(), setOf(), null)
    }

    fun findPathsTask2Impl(
        from: String,
        to: String,
        currentPath: List<String>,
        foundPaths: List<List<String>>,
        visitedSmallCaves: Set<String>,
        smallCaveVisitedTwice: String?
    ): List<List<String>>? {
        if (from == to) {
            return listOf(currentPath + from)
        }
        var newSmallCaveVisitedTwice = smallCaveVisitedTwice
        if (from in visitedSmallCaves) {
            if (from == "start") {
                return null
            } else if (smallCaveVisitedTwice == null) {
                newSmallCaveVisitedTwice = from
            } else {
                return null
            }
        }
        val newCurrentPath = currentPath + from
        val newVisitedSmallCaves = if (from[0].isLowerCase()) visitedSmallCaves + from else visitedSmallCaves
        val candidatesForNextPoint = connections[from] ?: listOf()
        val foundPathsFromHere = mutableListOf<List<String>>()
        for (nextPoint in candidatesForNextPoint) {
            val newPaths =
                findPathsTask2Impl(
                    nextPoint,
                    to,
                    newCurrentPath,
                    foundPaths + foundPathsFromHere,
                    newVisitedSmallCaves,
                    newSmallCaveVisitedTwice
                )
            if (newPaths != null) {
                foundPathsFromHere += newPaths
            }
        }
        return foundPathsFromHere
    }
}

class Day12 : AocDay<Long>() {
    override val name: String
        get() = "Day12"
    override val task1Spec: Long?
        get() = 10
    override val task2Spec: Long?
        get() = 36

    override fun task1(input: List<String>): Long {
        val map = CaveGraph()
        input.forEach {
            val edges = it.split("-")
            map.addConnectionPair(edges[0], edges[1])
        }
        return map.findPaths("start", "end")!!
            .distinctBy { it.joinToString(">") }
            .count()
            .toLong()
    }

    override fun task2(input: List<String>): Long {
        val map = CaveGraph()
        input.forEach {
            val edges = it.split("-")
            map.addConnectionPair(edges[0], edges[1])
        }
        return map.findPathsTask2("start", "end")!!
            .distinctBy { it.joinToString(">") }
            .count()
            .toLong()
    }
}

fun main() {
    val task = Day12()
    task.run()
}