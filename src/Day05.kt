import java.io.File

fun main(args: Array<String>) {
    println("2024 Advent of Code day 5")

    val input = File("day5input.txt").readText().split("\n\n").let { Pair(it[0], it[1]) }
    val rules = input.first.lines().map { line -> line.split("|").map { it.toInt() } }
    val updates = input.second.lines().map { line -> line.split(",").map { it.toInt() } }
    println("There are ${rules.size} rules and ${updates.size} updates to process")

    val part1 = updates.filterNot() { update ->
        rules.filter { update.containsAll(it) }.any { rule -> update.indexOf(rule[0]) > update.indexOf(rule[1]) }
    }.sumOf { it[it.size / 2] }
    println("The sum of the middle page numbers of the correctly ordered updates is $part1")
}

