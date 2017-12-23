import com.sun.org.apache.xpath.internal.operations.Bool

class CircularBuffer {
    var data = mutableListOf(0)
    var currentPos = 0
    var lastNumber = 0

    fun spinLock(steps: Int) {
        currentPos = (currentPos + steps) % data.size + 1
        lastNumber ++
        data.add(currentPos, lastNumber)
    }

    fun getNextNumber(): Int {
        return data[(currentPos + 1) % data.size]
    }

    fun getNumberAfterZero(): Int {
        return data[(data.indexOf(0) + 1) % data.size]
    }
}

fun cirBufferSimple(steps: Int, iterations: Int): Int {
    var size = 1
    var currPos = 0
    var result = 0
    for(inserted in 1..iterations) {
        currPos = (currPos + steps) % size
        size++
        currPos++
        if(currPos == 1) {
            result = inserted
        }
    }
    return result
}

fun Test17_1(): Boolean {
    var buffer = CircularBuffer()
    for(i in 1..2017) {
        buffer.spinLock(3)
    }
    println(buffer.data)
    return buffer.getNextNumber() == 638
}

fun Test17_2(): Boolean {
    val result = cirBufferSimple(3, 2017)
    return result == 1226
}

fun Solve17_1(): Int {
    var buffer = CircularBuffer()
    for(i in 1..2017) {
        buffer.spinLock(367)
    }
    return buffer.getNextNumber()
}

fun Solve17_2(): Int {
    return cirBufferSimple(367,50000000)
}

fun main(args: Array<String>){
    println(Test17_1())
    println(Test17_2())
    println(Solve17_1())
    println(Solve17_2())
}
