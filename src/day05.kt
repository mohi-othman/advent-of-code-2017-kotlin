import java.io.File
import java.io.InputStream

fun Solve04_1(input:String): Int {
    var list = input.trim().split("\n").map { x->x.trim().toInt() }.toMutableList()
    var escape = list.size
    var index = 0
    var steps = 0
    while(index < escape) {
        val old = list[index]
        list[index]++
        index += old
        steps++
    }
    println(list)
    return steps
}

fun Solve04_2(input:String): Int {
    var list = input.trim().split("\n").map { x->x.trim().toInt() }.toMutableList()
    var escape = list.size
    var index = 0
    var steps = 0
    while(index < escape) {
        val old = list[index]
        if(old >= 3) list[index]-- else list[index]++
        index += old
        steps++
    }
    println(list)
    return steps
}

fun LoadData05(): String {
    val inputStream: InputStream = File("data/day05").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    return inputString
}

fun main(args: Array<String>) {
    println(Solve04_2(LoadData05()))
}