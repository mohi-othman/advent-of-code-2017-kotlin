import java.io.File
import java.io.InputStream
import kotlin.math.max
import kotlin.math.min

var commandCache = hashMapOf<String, String>()
var resultCache = hashMapOf<String, String>()

fun String.spin(n:Int): String {
    return this.substring(this.length - n) + this.substring(0, this.length - n)
}

fun String.exchange(a: Int, b:Int): String {
    val x1 = min(a, b)
    val x2 = max(a, b)
    return this.substring(0, x1) +
            this[x2] +
            (if(x2-x1 > 1) this.substring(x1+1, x2) else "") +
            this[x1] +
            (if(x2<this.length-1) this.substring(x2+1) else "")
}


fun String.partner(x: Char, y: Char): String {
    val a = this.indexOf(x)
    val b = this.indexOf(y)
    return this.exchange(a,b)
}

fun String.parseDance(commands: String, iterations: Int = 1): String {
    var result = this
    for(i in 1..iterations) {
        result = resultCache.getOrPut(result, {
            for (command in commands.split(",")) {
                result = commandCache.getOrPut(result + command, {
                    var args = command.substring(1).trim()
                    when (command[0]) {
                        's' -> result.spin(args.toInt())
                        'x' -> result.exchange(args.split("/")[0].toInt(), args.split("/")[1].toInt())
                        'p' -> result.partner(args[0], args[2])
                        else -> ""
                    }
                })
            }
            result
        })
    }
    return result
}

fun LoadData16(): String {
    val inputStream: InputStream = File("data/day16.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    return inputString
}

fun Test16_1(): Boolean {
    var result = "abcde".parseDance("s1,x3/4,pe/b")
    return result == "baedc"
}

fun Test16_2(): Boolean {
    var result = "abcde".parseDance("s1,x3/4,pe/b", 2)
    return result == "ceadb"
}

fun Solve16_1(): String {
    var result = "abcdefghijklmnop"
    return result.parseDance(LoadData16())
}

fun Solve16_2(): String {
    var result = "abcdefghijklmnop"
    return result.parseDance(LoadData16(), 1000000000)
}

fun main(args: Array<String>) {
    println(Test16_1())
    println(Test16_2())
    println(Solve16_1())
    println(Solve16_2())
}