import java.io.File

fun main(args: Array<String>) {
    println("2024 Advent of Code day 3")

    val program = File("day3input.txt").readLines().joinToString("")
    println("There are ${program.length} characters of computer instructions")

    // Part 1
    val part1 = sumOfMults(program)
    println("Part 1: The sum of the multiplication operations in the program is $part1")

    // Part 2
    var trimmed = ""
    var remaining = program
    while (true) {
        val dontIndex = remaining.indexOf("don't()")
        if (dontIndex == -1) {
            trimmed += remaining
            break
        }
        trimmed += remaining.take(dontIndex)
        remaining = remaining.drop(dontIndex)
        val doIndex = remaining.indexOf("do()")
        if (doIndex == -1) break
        remaining = remaining.drop(doIndex)
    }
    val part2 = sumOfMults(trimmed)
    println("Part 2: The sum of the enabled multiplication operations in the program is $part2")
}

fun sumOfMults(program: String): Int {
    val PROGRAM_REGEX = """mul\((\d+),(\d+)\)""".toRegex()
    var total = 0
    val matchResults = PROGRAM_REGEX.findAll(program)
    matchResults.forEach { result ->
        total += result.groups[1]!!.value.toInt() * result.groups[2]!!.value.toInt()
    }
    return total
}