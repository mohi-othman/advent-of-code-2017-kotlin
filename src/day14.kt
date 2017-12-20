fun String.getKnotHashMemory(): String {
    var data = (0..255).toMutableList()
    data.knotHash(this.toLengthsList().padLengths(), 64)
    val hash = data.condenseKnotHash()
    var result = ""
    for(char in hash) {
        var byte = char.toString().toInt(16).toString(2)
        for(i in (1..4-byte.length)) result += "0"
        result += byte
    }
    return result
}

fun getMemoryState(key: String): List<String> {
    return (0..127).map { "$key-$it".getKnotHashMemory() }
}

fun List<String>.getUsedCount(): Int {
    return this.sumBy { line -> line.count { char -> char == '1' } }
}

fun List<String>.getGroupCount(): Int {
    var visited = mutableListOf<Pair<Int,Int>>()
    var groupCount = 0

    fun visit(row:Int, col: Int): Boolean {
        val cell = Pair(row,col)
        if(!visited.contains(cell)) {
            visited.add(cell)
            if(row < this.size-1 && this[row+1][col] == '1')
                visit(row+1, col)
            if(col < this[row].length-1 && this[row][col+1] == '1')
                visit(row, col+1)
            if(row > 0 && this[row-1][col] == '1')
                visit(row-1, col)
            if(col > 0 && this[row][col-1] == '1')
                visit(row, col-1)
            return true
        }
        return false
    }

    for(row in 0 until this.size) {
        for(col in 0 until this[row].length) {
            if(this[row][col] == '1') {
                if(visit(row,col)) groupCount ++
            }
        }
    }
    return groupCount
}

fun Test14_1(): Boolean {
    return getMemoryState("flqrgnkx").getUsedCount() == 8108
}

fun Test14_2(): Boolean {
    return getMemoryState("flqrgnkx").getGroupCount() == 1242
}

fun Solve14_1(): Int {
    return getMemoryState("jxqlasbh").getUsedCount()
}

fun Solve14_2(): Int {
    return getMemoryState("jxqlasbh").getGroupCount()
}

fun main(args: Array<String>) {
    println(Test14_1())
    println(Test14_2())

    println(Solve14_1())
    println(Solve14_2())
}