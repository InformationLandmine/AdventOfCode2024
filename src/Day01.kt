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
    var total = 0
    list1.zip(list2).forEach { pair -> total += abs(pair.first - pair.second) }
    println("Part 1: The total distance between the two lists is $total")

    // Part 2
    var similarity = 0
    list1.forEach { it ->
        val count = list2.count{ item -> item == it}
        similarity += it * count
    }
    println("Part 2: The similarity between the two lists is $similarity")
}