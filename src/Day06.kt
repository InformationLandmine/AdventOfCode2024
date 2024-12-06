import java.awt.Point
import java.io.File

fun main(args: Array<String>) {
    println("2024 Advent of Code day 6")

    val input = File("day6input.txt").readLines().map { line ->
        line.map { GuardMapPoint(it) }
    }
    val guardMap = GuardMap(input)
    println("The guard map is ${guardMap.width} by ${guardMap.height} locations")
    println("The guard is at ${guardMap.guardLocation} facing ${guardMap.guardDirection}")

    // Part 1
    do {
        guardMap.moveGuard()
    } while (guardMap.isOnMap(guardMap.guardLocation))
    println("The number of locations visited by the guard is ${guardMap.visitedCount}")

//    // Part 2
//    do {
//        guardMap.moveGuard()
//    } while (guardMap.isOnMap(guardMap.guardLocation))
//    println("The number of locations visited by the guard is ${guardMap.visitedCount}")
}

class GuardMapPoint(var contents: Char) {
    var visited = false
    val directions: MutableSet<Direction> = mutableSetOf()
}

class GuardMap(private val map: List<List<GuardMapPoint>>) {
    var guardLocation = Point()
    var guardDirection = Direction.North

    init {
        val guardY = map.indexOfFirst { it.any { point -> "<v>^".contains(point.contents) } }
        val guardX = map[guardY].indexOfFirst { "<v>^".contains(it.contents) }
        guardLocation = Point(guardX, guardY)
        guardDirection = when (contentsAt(guardLocation)) {
            '^' -> Direction.North
            'v' -> Direction.South
            '>' -> Direction.East
            '<' -> Direction.West
            else -> throw Exception("Invalid guard direction: ${contentsAt(guardLocation)}")
        }
        markVisited(guardLocation, guardDirection)
    }

    val width get() = map[0].size
    val height get() = map.size
    val visitedCount get() = map.sumOf { row -> row.count { it.visited } }

    fun contentsAt(p: Point) = map[p.y][p.x].contents
    fun isOnMap(p: Point) = p.x >= 0 && p.y >=0 && p.x < width && p.y < height
    fun isClear(p: Point) = isOnMap(p) && contentsAt(p) != '#'
    fun wasHere(p: Point, d: Direction): Boolean {
        val gp = map[p.y][p.x]
        return (gp.visited && gp.directions.contains(d))
    }

    fun markVisited(p: Point, d: Direction) {
        map[p.y][p.x].visited = true
        map[p.y][p.x].directions.add(d)
    }

    fun turnGuard() {
        guardDirection = when (guardDirection) {
            Direction.North -> Direction.East
            Direction.South -> Direction.West
            Direction.East -> Direction.South
            Direction.West -> Direction.North
        }
        markVisited(guardLocation, guardDirection)
    }
    fun moveGuard(): Boolean {
        var hereBefore = false
        val newLoc = Point(guardLocation.x, guardLocation.y)
        when (guardDirection) {
            Direction.North -> newLoc.y -= 1
            Direction.South -> newLoc.y += 1
            Direction.East -> newLoc.x += 1
            Direction.West -> newLoc.x -= 1
        }
        if (!isOnMap(newLoc)) {
            guardLocation = newLoc
        } else if (isClear(newLoc)) {
            guardLocation = newLoc
            if (wasHere(guardLocation, guardDirection)) hereBefore = true
            markVisited(guardLocation, guardDirection)
        }
        else turnGuard()
        return hereBefore
    }
}

enum class Direction { North, South, East, West }