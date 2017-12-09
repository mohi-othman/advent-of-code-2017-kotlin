import java.io.File
import java.io.InputStream

data class Day09Result(val groupScore: Int, val garbageScore: Int)

fun ParseString(input: String): Day09Result {
    var baseValue = 0
    var groupScore = 0
    var garbageScore = 0
    var inGarbage = false
    var ignoreNext = false

    for(char in input) {
        if(ignoreNext) {
            ignoreNext = false
            continue
        }
        when {
            (char == '{' && !inGarbage) -> {
                baseValue ++
                groupScore += baseValue
            }
            (char == '}' && !inGarbage) -> {
                baseValue --
            }
            (char == '<' && !inGarbage) -> {
                inGarbage = true
            }
            (char == '>' && inGarbage) -> {
                inGarbage = false
            }
            (char == '!') -> {
                ignoreNext = true
            }
            (inGarbage) -> {
                garbageScore++
            }

        }
    }
    return Day09Result(groupScore, garbageScore)
}

fun Test09_1(): Boolean {
    return  (ParseString("{}").groupScore == 1 &&
            ParseString("{{{}}}").groupScore == 6 &&
            ParseString("{{},{}}").groupScore == 5 &&
            ParseString("{{{},{},{{}}}}").groupScore == 16 &&
            ParseString("{<a>,<a>,<a>,<a>}").groupScore == 1 &&
            ParseString("{{<ab>},{<ab>},{<ab>},{<ab>}}").groupScore == 9 &&
            ParseString("{{<!!>},{<!!>},{<!!>},{<!!>}}").groupScore == 9 &&
            ParseString("{{<a!>},{<a!>},{<a!>},{<ab>}}").groupScore == 3)
}

fun Test09_2(): Boolean {
    return  (ParseString("<>").garbageScore == 0 &&
            ParseString("<random characters>").garbageScore == 17 &&
            ParseString("<<<<>").garbageScore == 3 &&
            ParseString("<{!>}>").garbageScore == 2 &&
            ParseString("<!!>").garbageScore == 0 &&
            ParseString("<!!!>>").garbageScore == 0 &&
            ParseString("<{o\"i!a,<{i<a>").garbageScore == 10)
}

fun LoadData09(): String {
    val inputStream: InputStream = File("data/day09.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    return inputString
}

fun main(args: Array<String>) {
    println(Test09_1())
    println(Test09_2())
    val result =ParseString(LoadData09())
    println(result.groupScore)
    println(result.garbageScore)
}