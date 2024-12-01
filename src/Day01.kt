import java.io.File
import kotlin.math.abs

fun main(args: Array<String>) {
    println("2024 Advent of Code day 1")

    val list1 = ArrayList<Int>()
    val list2 = ArrayList<Int>()
    File("day1input.txt").readLines().forEach { line ->
        list1.add(line.split(" ").first().trim().toInt())
        list2.add(line.split(" ").last().trim().toInt())
    }
    println("There are ${list1.size} entries in the locations list")

    // Part 1
    list1.sort()
    list2.sort()
    val part1 = list1.zip(list2).sumOf { pair -> abs(pair.first - pair.second) }
    println("Part 1: The total distance between the two lists is $part1")

    // Part 2
    val part2 = list1.sumOf { it * list2.count { item -> item == it} }
    println("Part 2: The similarity between the two lists is $part2")
}