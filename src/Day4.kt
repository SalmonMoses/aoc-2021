class Board(val numbers: Array<IntArray>) {
    var checked = Array(numbers.size) { BooleanArray(numbers[0].size) { false } }

    companion object {
        fun fromString(desc: String): Board {
            val rows = desc.split("\n")
            val rowNumber = rows.size
            var numbers = mutableListOf<IntArray>()
            rows.forEach {
                val rowNumbers = it.replace(Regex("  "), " ")
                    .split(" ")
                    .filter { it.isNotEmpty() }
                    .map { it.toInt() }
                    .toIntArray()
                numbers.add(rowNumbers)
            }
            return Board(numbers.toTypedArray())
        }
    }

    fun checkNumber(number: Int) {
        for (i in 0 until numbers.size) {
            for (j in 0 until numbers[0].size) {
                if (numbers[i][j] == number) {
                    checked[i][j] = true
                }
            }
        }
    }

    fun score(k: Int): Int {
        var unmarkedSum = 0
        for (i in 0 until numbers.size) {
            for (j in 0 until numbers[0].size) {
                if (!checked[i][j]) {
                    unmarkedSum += numbers[i][j]
                }
            }
        }
        return unmarkedSum * k
    }

    private fun hasWonHorizontally(): Boolean {
        for (i in 0 until numbers.size) {
            var winningRow = true
            for (j in 0 until numbers[0].size) {
                if (!checked[i][j]) {
                    winningRow = false
                    break
                }
            }
            if (winningRow) return true
        }
        return false
    }

    private fun hasWonVertically(): Boolean {
        for (i in 0 until numbers[0].size) {
            var winningRow = true
            for (j in 0 until numbers.size) {
                if (!checked[j][i]) {
                    winningRow = false
                    break
                }
            }
            if (winningRow) return true
        }
        return false
    }

    fun hasWon(): Boolean {
        return hasWonVertically() || hasWonHorizontally()
    }
}

class Day4 : AocDay<Int>() {
    override val name: String
        get() = "Day4"
    override val task1Spec: Int?
        get() = 4512
    override val task2Spec: Int?
        get() = 1924

    override fun task1(input: List<String>): Int {
        val drawnNumbers = input[0].split(",").map { it.toInt() }
        var boardDescs = mutableListOf<String>()
        var currentBoard = ""
        var boards = mutableListOf<Board>()
        for (i in 2 until input.size) {
            if (input[i] == "") {
                val board = Board.fromString(currentBoard)
                currentBoard = ""
                boards.add(board)
            } else {
                if (currentBoard != "") {
                    currentBoard += "\n"
                }
                currentBoard += input[i]
            }
        }
        val board = Board.fromString(currentBoard)
        boards.add(board)
        for (drawn in drawnNumbers) {
            for (board in boards) {
                board.checkNumber(drawn)
                if (board.hasWon()) {
                    return board.score(drawn)
                }
            }
        }
        return 0
    }

    override fun task2(input: List<String>): Int {
        val drawnNumbers = input[0].split(",").map { it.toInt() }
        var boardDescs = mutableListOf<String>()
        var currentBoard = ""
        var boards = mutableListOf<Board>()
        for (i in 2 until input.size) {
            if (input[i] == "") {
                val board = Board.fromString(currentBoard)
                currentBoard = ""
                boards.add(board)
            } else {
                if (currentBoard != "") {
                    currentBoard += "\n"
                }
                currentBoard += input[i]
            }
        }
        val board = Board.fromString(currentBoard)
        boards.add(board)
        var wonBoardsNumber = 0
        for (drawn in drawnNumbers) {
            for (board in boards) {
                if (board.hasWon()) continue
                board.checkNumber(drawn)
                if (board.hasWon()) {
                    wonBoardsNumber += 1
                    if (wonBoardsNumber == boards.size) {
                        return board.score(drawn)
                    }
                }
            }
        }
        return 0
    }
}

fun main() {
    val task = Day4()
    task.run()
}