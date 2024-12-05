import java.io.File
import java.util.Collections

fun main(args: Array<String>) {
    println("2024 Advent of Code day 5")

    val input = File("day5input.txt").readText().split("\n\n").let { Pair(it[0], it[1]) }
    val rules = input.first.lines().map { line -> line.split("|").map { it.toInt() } }
    val updates = input.second.lines().map { line -> line.split(",").map { it.toInt() }.toMutableList() }
    println("There are ${rules.size} rules and ${updates.size} updates to process")

    // Part 1
    val part1 = updates.filterNot { update ->
        rules.filter { update.containsAll(it) }.any { rule -> update.indexOf(rule[0]) > update.indexOf(rule[1]) }
    }.sumOf { it[it.size / 2] }
    println("The sum of the middle page numbers of the correctly ordered updates is $part1")

    // Part 2
    val failedUpdates = updates.filter { update ->
        rules.filter { update.containsAll(it) }.any { rule -> update.indexOf(rule[0]) > update.indexOf(rule[1]) }
    }
    failedUpdates.forEach { update ->
        while (true) {
            when (val rule = rules.filter { update.containsAll(it) }.firstOrNull { rule -> update.indexOf(rule[0]) > update.indexOf(rule[1]) }) {
                null -> break
                else -> Collections.swap(update, update.indexOf(rule[0]), update.indexOf(rule[1]))
            }
        }
    }
    val part2 = failedUpdates.sumOf { it[it.size / 2] }
    println("The sum of the middle page numbers of the incorrectly ordered updates after reordering is $part2")
}

