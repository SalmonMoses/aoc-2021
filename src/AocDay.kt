import java.io.File

abstract class AocDay<T> {
    abstract val name: String
    abstract val task1Spec: T?
    abstract val task2Spec: T?
    abstract fun task1(input: List<String>): T
    abstract fun task2(input: List<String>): T

    private fun readInput(): List<String> = readInput(name)
    private fun readTaskSpecInput(): List<String> = readInput("spec/$name.spec")
    private fun readTask1SpecInput(): List<String> = readInput("spec/$name.spec1")
    private fun readTask2SpecInput(): List<String> = readInput("spec/$name.spec2")

    fun run() {
        if (task1Spec != null) {
            val spec = if (File("spec/$name.spec1.txt").exists()) {
                readTask1SpecInput()
            } else {
                readTaskSpecInput()
            }
            val time = System.currentTimeMillis()
            val task1Actual = task1(spec)
            val failed = task1Actual != task1Spec
            println("Task 1 Spec = $task1Actual ${if(failed) "!= $task1Spec - Failed!" else "== $task1Spec"} [${System.currentTimeMillis() - time} ms]")
        }
        if (task2Spec != null) {
            val spec = if (File("spec/$name.spec2.txt").exists()) {
                readTask2SpecInput()
            } else {
                readTaskSpecInput()
            }
            val time = System.currentTimeMillis()
            val task2Actual = task2(spec)
            val failed = task2Actual != task2Spec
            println("Task 2 Spec = $task2Actual ${if(failed) "!= $task2Spec - Failed!" else "== $task2Spec"} [${System.currentTimeMillis() - time} ms]")
        }
        val input = readInput()
        check(input.isNotEmpty(), { "Input is empty!" })
        var time = System.currentTimeMillis()
        val task1Solution = task1(input)
        println("Task 1 Solution = $task1Solution [${System.currentTimeMillis() - time} ms]")
        time = System.currentTimeMillis()
        val task2Solution = task2(input)
        println("Task 2 Solution = $task2Solution [${System.currentTimeMillis() - time} ms]")
    }
}