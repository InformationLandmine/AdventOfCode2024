import java.io.File
import kotlin.collections.sorted
import kotlin.collections.sortedDescending
import kotlin.math.abs

fun main(args: Array<String>) {
    println("2024 Advent of Code day 2")

    val reports = File("day2input.txt").readLines().map { line ->
        line.split(" ").map { it.toInt() }
    }
    println("There are ${reports.size} entries in the locations list")

    // Part 1 - see if the lists are in ascending or descending order already
    val part1 = reports.count { isSafe(it) }
    println("Part 1: The number of safe reports is $part1")

    // Part 2
    val part2 = reports.count { isSafe2(it) }
    println("Part 2: The number of safe reports is $part2")
}

fun isSafe(report: List<Int>) : Boolean {
    if (report.sorted() != report && report.sortedDescending() != report)
        return false
    if (report.windowed(2, 1).any { abs(it.first() - it.last()) > 3 || abs(it.first() - it.last()) < 1 })
        return false
    return true
}

fun isSafe2(report: List<Int>) : Boolean {
    val ascending = isAscending(report)
    var isSafe = false
    var badIndex: Int = -1
    report.windowed(2, 1).forEachIndexed { i, level ->
        if (badIndex == -1) {
            if (ascending) {
                if (level.first() >= level.last()) {
                    badIndex = i
                } else if (level.last() - level.first() > 3) {
                    badIndex = i
                }
            } else {
                if (level.first() <= level.last()) {
                    badIndex = i
                } else if (level.first() - level.last() > 3) {
                    badIndex = i
                }
            }
        }
    }

    if (badIndex == -1) {
        isSafe = true
    } else {
        // try removing first and second items from window and test
        if (isSafe(report.filterIndexed { index, _ -> index != badIndex })) {
            isSafe = true
        } else if (isSafe(report.filterIndexed { index, _ -> index != badIndex + 1 })) {
            isSafe = true
        }
    }

    return isSafe
}

fun isAscending(report: List<Int>): Boolean {
    var count = 0
    if (report[0] < report[1]) count ++
    if (report[1] < report[2]) count ++
    if (report[2] < report[3]) count ++
    if (report[3] < report[4]) count ++
    if (count >= 2) return true else return false
}
