import java.io.File
import java.io.InputStream

fun IsNoDupe(passphrase: String): Boolean {
    val words = passphrase.trim().split(" ")
    return (words.size == words.distinct().size)
}

fun IsNoAnagram(passphrase: String): Boolean {
    val words = passphrase.trim().split(" ")
            .map { x ->
                x.toCharArray()
                        .sortedBy { x -> x }
            }

    return (words.size == words.distinct().size)
}

fun Solve_04_1(input: String): Int {
    var result = 0
    for(line in input.split("\n")) {
        if(IsNoDupe(line)) result++
    }
    return result
}

fun Solve_04_2(input: String): Int {
    var result = 0
    for(line in input.split("\n")) {
        if(IsNoAnagram(line)) result++
    }
    return result
}

fun LoadData(): String {
    val inputStream: InputStream = File("data/day04").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    return inputString
}

fun main(args: Array<String>) {
    val input = LoadData()
    println(Solve_04_1(input))
    println(Solve_04_2(input))
}

