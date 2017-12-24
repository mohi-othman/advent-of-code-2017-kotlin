import java.io.File
import java.io.InputStream

class TraverseResult(val stepCount: Int, val collected: String)

fun traverseRoutingDiagram(diagram: String): TraverseResult {
    val mapTemp = diagram.split(Regex("[\\r\\n]+"))

    //Equalize dimensions
    val maxX = mapTemp.map { it.length }.max()!! - 1
    val maxY = mapTemp.size - 1
    val map = mapTemp.map {
        if (it.length < maxX + 1) {
            var newRow = it
            for (i in 1..maxX - it.length + 1)
                newRow += " "
            newRow
        } else {
            it
        }
    }

    var x = if (map[0].contains('|')) {
        map[0].indexOf('|')
    } else if (map[0].contains('-')) {
        map[0].indexOf('-')
    } else if (map[0].contains('+')) {
        map[0].indexOf('+')
    } else {
        0
    }
    var y = 0
    var dirX = 0
    var dirY = 1
    var result = ""
    var count = 1

    loop@ while (true) {
        count++
        x += dirX
        y += dirY
        val char = map[y][x]

        if (char == ' ' || x < 0 || x > maxX || y < 0 || y > maxY) {
            count--
            return TraverseResult(count, result)
        }

        when (char) {
            '|', '-' -> continue@loop
            '+' -> {
                if (dirX != 1 && x > 0 && map[y][x - 1] != ' ') {
                    dirX = -1
                    dirY = 0
                } else if (dirX != -1 && x < maxX && map[y][x + 1] != ' ') {
                    dirX = 1
                    dirY = 0
                } else if (dirY != 1 && y > 0 && map[y - 1][x] != ' ') {
                    dirX = 0
                    dirY = -1
                } else if (dirY != -1 && y < maxY && map[y + 1][x] != ' ') {
                    dirX = 0
                    dirY = 1
                } else {
                    count--
                    return TraverseResult(count, result)
                }
            }
            else -> result += char
        }
    }
}

val testData = "     |          \n" +
        "     |  +--+    \n" +
        "     A  |  C    \n" +
        " F---|----E|--+ \n" +
        "     |  |  |  D \n" +
        "     +B-+  +--+ \n"

fun LoadData19(): String {
    val inputStream: InputStream = File("data/day19.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    return inputString
}

fun Test19_1(): Boolean {
    return traverseRoutingDiagram(testData).collected == "ABCDEF"
}

fun Test19_2(): Boolean {
    return traverseRoutingDiagram(testData).stepCount == 38
}

fun Solve19_1(): String {
    return traverseRoutingDiagram(LoadData19()).collected
}

fun Solve19_2(): Int {
    return traverseRoutingDiagram(LoadData19()).stepCount
}

fun main(args: Array<String>) {
    println(Test19_1())
    println(Test19_2())
    println(Solve19_1())
    println(Solve19_2())
}