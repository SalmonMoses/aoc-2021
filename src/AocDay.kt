import java.io.File

abstract class AocDay<T> {
    abstract val name: String
    abstract val task1Spec: T?
    abstract val task2Spec: T?
    abstract fun task1(input: List<String>): T
    abstract fun task2(input: List<String>): T

    fun readInput(): List<String> = readInput(name)
    fun readTaskSpecInput(): List<String> = readInput("spec/$name.spec")
    fun readTask1SpecInput(): List<String> = readInput("spec/$name.spec1")
    fun readTask2SpecInput(): List<String> = readInput("spec/$name.spec2")

    fun run() {
        if (task1Spec != null) {
            val spec = if (File("spec/$name.spec1.txt").exists()) {
                readTask1SpecInput()
            } else {
                readTaskSpecInput()
            }
            val task1Actual = task1(spec)
            check(task1Actual == task1Spec, { "Task 1 check failed with $task1Actual" })
        }
        if (task2Spec != null) {
            val spec = if (File("spec/$name.spec2.txt").exists()) {
                readTask2SpecInput()
            } else {
                readTaskSpecInput()
            }
            val task2Actual = task2(spec)
            check(task2Actual == task2Spec, { "Task 2 check failed with $task2Actual" })
        }
        val input = readInput()
        check(input.isNotEmpty(), { "Input is empty!" })
        println(task1(input))
        println(task2(input))
    }
}