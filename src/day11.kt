// Uses hex grid coordinate system from http://keekerdc.com/2011/03/hexagon-grids-coordinate-systems-and-distance-calculations/
import java.io.File
import java.io.InputStream
import kotlin.math.abs
import kotlin.math.max

data class Hex(var x: Int, var y: Int, var z: Int) {
    var maxDistance: Int = 0
}

fun Hex.move(dir: String) {
    when(dir) {
        "n" -> {
            this.y++
            this.z--
        }
        "s" -> {
            this.y--
            this.z++
        }
        "ne" -> {
            this.x++
            this.z--
        }
        "nw" -> {
            this.y++
            this.x--
        }
        "se" -> {
            this.y--
            this.x++
        }
        "sw" -> {
            this.x--
            this.z++
        }
    }
    this.maxDistance = max(this.maxDistance, this.distanceToOrigin())
}

fun Hex.distanceToOrigin(): Int {
    return max(abs(this.x), max(abs(this.y), abs(this.z)))
}

fun track(input: String): Hex {
    var myHex = Hex(0,0,0)
    for(step in input.split(",").map { x->x.trim() }) {
        myHex.move(step)
    }
    return myHex
}

fun LoadData11(): String {
    val inputStream: InputStream = File("data/day11.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    return inputString
}
fun Test11_1(): Boolean {
    return track("ne,ne,ne").distanceToOrigin() == 3 &&
            track("ne,ne,sw,sw").distanceToOrigin() == 0 &&
            track("ne,ne,s,s").distanceToOrigin() == 2 &&
            track("se,sw,se,sw,sw").distanceToOrigin() == 3
}

fun Solve11_1(hex: Hex): Int {
    return hex.distanceToOrigin()
}

fun Solve11_2(hex: Hex): Int {
    return hex.maxDistance
}

fun main(args: Array<String>) {
    println(Test11_1())
    val hex = track(LoadData11())
    println(Solve11_1(hex))
    println(Solve11_2(hex))
}