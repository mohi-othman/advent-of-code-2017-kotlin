import java.io.File
import java.io.InputStream

class Node(val id: Int, val connected: List<Int>)

fun buildNetwork(input: String): List<Node> {
    return input.split("\n")
            .map {
                var splits = it.split("<->")
                Node(splits[0].trim().toInt(), splits[1].split(",").map { x->x.trim().toInt() }.toList())
            }
}

fun List<Node>.findAllConnected(id: Int): List<Int> {
    var visited = mutableSetOf<Int>()
    var toVisit = mutableListOf(this.find { it.id == id } !!)
    while (toVisit.size > 0) {
        var newToVisit = mutableListOf<Node>()
        for(node in toVisit) {
            visited.add(node.id)
            newToVisit.addAll(node.connected
                    .filter { !visited.contains(it) }
                    .map { this.find { x-> x.id == it } !! })
        }
        toVisit = newToVisit
    }
    return visited.toList()
}

fun List<Node>.countGroups(): Int{
    var toVisit = this.map {x->x.id}.toMutableList()
    var count = 0
    while(toVisit.size > 0) {
        val group = this.findAllConnected(toVisit.first())
        count++
        toVisit.removeAll(group)
    }
    return count
}

fun LoadData12(): String {
    val inputStream: InputStream = File("data/day12.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    return inputString
}

fun Test12_1(): Boolean {
    var list = buildNetwork("0 <-> 2\n" +
            "1 <-> 1\n" +
            "2 <-> 0, 3, 4\n" +
            "3 <-> 2, 4\n" +
            "4 <-> 2, 3, 6\n" +
            "5 <-> 6\n" +
            "6 <-> 4, 5")
    var result = list.findAllConnected(0)

    return result.size == 6
}

fun Test12_2(): Boolean {
    var list = buildNetwork("0 <-> 2\n" +
            "1 <-> 1\n" +
            "2 <-> 0, 3, 4\n" +
            "3 <-> 2, 4\n" +
            "4 <-> 2, 3, 6\n" +
            "5 <-> 6\n" +
            "6 <-> 4, 5")
    var result = list.countGroups()

    return result == 2
}

fun Solve12_1(network: List<Node>): Int {
    return network.findAllConnected(0).size
}

fun Solve12_2(network: List<Node>): Int {
    return network.countGroups()
}

fun main(args: Array<String>) {
    println(Test12_1())
    println(Test12_2())

    val network = buildNetwork(LoadData12())
    println(Solve12_1(network))
    println(Solve12_2(network))
}