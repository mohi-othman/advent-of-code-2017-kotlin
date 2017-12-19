import kotlin.math.floor

fun MutableList<Int>.knotHash(lengths: List<Int>, iterations: Int = 1) {
    var pos = 0
    var skip = 0
    val size = this.size
    for(i in 1..iterations) {
        for(length in lengths) {
            val half = floor(length/2.0).toInt()
            for(index in 0..(half-1)) {
                var posA = (pos + index) % size
                var posB = (posA + (half - index) * 2 - 1) % size
                if(length%2==1) posB = (posB + 1) % size
                val old = this[posA]
                this[posA] = this[posB]
                this[posB] = old
            }
            pos = (pos + length + skip) % size
            skip++
        }
    }
}

fun MutableList<Int>.condenseKnotHash(): String {
    return (0..15).map {
        val pos = it*16
        var value = this[pos]
        for(j in pos+1..pos+15) {
            value = value xor this[j]
        }
        var hex = value.toString(16)
        if(value < 16) hex = "0" + hex
        hex
    }.joinToString("")
}

fun String.toLengthsList(): List<Int> {
    return this.map{ it.toInt()}
}

fun List<Int>.padLengths(): List<Int> {
    return this + listOf(17, 31, 73, 47, 23)
}

fun Test10_1(): Boolean {
    var data = (0..4).toMutableList()
    data.knotHash(listOf(3,4,1,5))
    return (data[0]*data[1] == 12)
}

fun Test10_2(): Boolean {
    var lengths = listOf("","AoC 2017","1,2,3","1,2,4")
    var results = listOf("a2582a3a0e66e6e86e3812dcb672a272",
            "33efeb34ea91902bb2f59c9920caa6cd",
            "3efbe78a8d82f29979031a4aa0b16a9d",
            "63960835bcdc130f0b66d7ff4f6a5a8e")
    for(i in 0..3) {
        var data = (0..255).toMutableList()
        data.knotHash(lengths[i].toLengthsList().padLengths(), 64)
        if(data.condenseKnotHash() != results[i])
            return false
    }
    return true
}

fun Solve10_1(): Int {
    var data = (0..255).toMutableList()
    data.knotHash(listOf(18,1,0,161,255,137,254,252,14,95,165,33,181,168,2,188))
    return data[0]*data[1]
}

fun Solve10_2(): String {
    var data = (0..255).toMutableList()
    data.knotHash("18,1,0,161,255,137,254,252,14,95,165,33,181,168,2,188".toLengthsList().padLengths(), 64)
    return data.condenseKnotHash()
}
fun main(args: Array<String>) {
    println(Test10_1())
    println(Test10_2())
    println(Solve10_1())
    println(Solve10_2())
}