import java.io.File

fun main(args: Array<String>) {
    println("2024 Advent of Code day 2")

    val reports = File("day2input.txt").readLines().map { line -> line.split(" ").map { it.toInt() } }
    println("There are ${reports.size} entries in the locations list")

    // Part 1
    val part1 = reports.count { isSafe(it).first }
    println("Part 1: The number of safe reports is $part1")

    // Part 2
    val part2 = reports.count { isSafe2(it) }
    println("Part 2: The number of safe reports is $part2")
}

fun isSafe(report: List<Int>) : Triple<Boolean, Int, Int> {
    val ascFailedIndex = report.windowed(2, 1).map { it[0] - it[1] >= -3 && it[0] - it[1] < 0 }.indexOfFirst { !it }
    val descFailedIndex = report.windowed(2, 1).map { it[0] - it[1] <= 3 && it[0] - it[1] > 0 }.indexOfFirst { !it }
    return Triple(ascFailedIndex == -1 || descFailedIndex == -1, ascFailedIndex, descFailedIndex)
}

fun isSafe2(report: List<Int>) : Boolean {
    val firstCheck = isSafe(report)
    if (firstCheck.first) return true
    val indicesToRemove = setOf(firstCheck.second, firstCheck.second + 1, firstCheck.third, firstCheck.third + 1)
    return indicesToRemove.any { errorIndex -> isSafe(report.filterIndexed { i, _ -> i != errorIndex }).first }
}
