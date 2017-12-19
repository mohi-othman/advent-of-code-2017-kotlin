import java.io.File
import java.io.InputStream
import kotlin.math.floor

class Firewall(val level: Int, val range: Int) {
    fun positionAt(time: Int): Int {
        if(time==0) return 0
        val pos = (time-1) % (range-1)
        val dir = floor((time-1).toDouble()/(range-1)).toInt() % 2
        if(dir==0) {
            return pos + 1
        } else {
            return range - pos - 2
        }
    }
}

fun List<Firewall>.traverse(): Int {
    var result = 0

    for(firewall in this.sortedBy { x->x.level }) {
        if(firewall.positionAt(firewall.level) == 0) {
            result += firewall.level * firewall.range
        }
    }
    return result
}

fun List<Firewall>.findEscapeDelay(): Int {
    var time = 9
    var pass = false
    while(!pass) {
        time++
        pass = true
        for(firewall in this.sortedBy { x->x.level }) {
            if(firewall.positionAt(time + firewall.level) == 0) {
                pass = false
                break
            }
        }
    }
    return time
}

fun buildFirewalls(input: String): List<Firewall> {
    return input.trim().split("\n")
            .map {
                val splits = it.split(":")
                Firewall(splits[0].trim().toInt(),splits[1].trim().toInt())
    }
}

fun LoadData13(): String {
    val inputStream: InputStream = File("data/day13.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    return inputString
}

fun Test13_1(): Boolean {
    var firewalls = buildFirewalls("0: 3\n" +
            "1: 2\n" +
            "4: 4\n" +
            "6: 4")
    return firewalls.traverse() == 24
}

fun Test13_2(): Boolean {
    var firewalls = buildFirewalls("0: 3\n" +
            "1: 2\n" +
            "4: 4\n" +
            "6: 4")
    return firewalls.findEscapeDelay() == 10
}

fun Solve13_1(fireWalls: List<Firewall>): Int {
    return fireWalls.traverse()
}

fun Solve13_2(fireWalls: List<Firewall>): Int {
    return fireWalls.findEscapeDelay()
}

fun main(args: Array<String>) {
    println(Test13_1())
    println(Test13_2())

    val fireWalls = buildFirewalls(LoadData13())
    println(Solve13_1(fireWalls))
    println(Solve13_2(fireWalls))
}