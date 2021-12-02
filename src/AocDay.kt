abstract class AocDay<T> {
    abstract val name: String
    abstract val task1Spec: T
    abstract val task2Spec: T
    abstract fun task1(input: List<String>): T
    abstract fun task2(input: List<String>): T

    fun readInput(): List<String> = readInput(name)
    fun readTask1SpecInput(): List<String> = readInput("spec/$name.spec1")
    fun readTask2SpecInput(): List<String> = readInput("spec/$name.spec2")

    fun run() {
        val spec1 = readTask1SpecInput()
        check(task1(spec1) == task1Spec, { "Task 1 check failed" })
        val spec2 = readTask2SpecInput()
        check(task2(spec2) == task2Spec, { "Task 2 check failed" })
        val input = readInput()
        check(input.isNotEmpty(), { "Input is empty!" })
        println(task1(input))
        println(task2(input))
    }
}