import kotlin.math.ceil
import kotlin.math.sqrt

fun Solve03_1(number: Int): Int {
    var side = ceil(sqrt(number.toDouble()))
    if (side % 2 == 0.0) side++
    val x = (side - 1) / 2
    var y = x
    var inc = -1
    for(i in  1..(side*side).toInt()-number) {
        y += inc
        if(y==0.0 || y==x) inc = -inc
    }

    return x.toInt() + y.toInt()
}

fun Solve03_2(stop: Int): Int {
    var inner = mutableListOf(1,2,4,5,10,11,23,25)
    var outer = mutableListOf<Int>()
    var last = 0
    var innerIndex = inner.size - 1
    var size = 5

    while (true) {
        for (side in 0..3) {
            for (outerIndex in 0..(size - 2)) {
                var newNumber = 0
                if (outerIndex == 0) {
                    newNumber = last + inner[innerIndex] + inner[(innerIndex + 1) % inner.size]
                    if (outer.size > 1) {
                        newNumber += outer[outer.size - 2]
                    }
                } else if (outerIndex == (size - 2)) {
                    newNumber = last + inner[innerIndex]
                    if (side == 3) {
                        newNumber += outer.first()
                    }
                } else if (outerIndex == (size - 3)) {
                    innerIndex = (innerIndex + 1) % inner.size
                    newNumber = last + inner[innerIndex] + inner[(innerIndex + inner.size - 1) % inner.size]
                    if (side == 3) {
                        newNumber += outer.first()
                    }
                } else {
                    innerIndex = (innerIndex + 1) % inner.size
                    newNumber = last + inner[innerIndex] + inner[(innerIndex + 1) % inner.size] + inner[(innerIndex + inner.size - 1) % inner.size]
                }
                if (newNumber > stop)
                    return newNumber
                outer.add(newNumber)
                last = newNumber
            }
        }
        inner = outer.toMutableList()
        outer.clear()
        last = 0
        innerIndex = inner.size - 1
        size = size + 2
    }
    return 0
}

fun main(args: Array<String>) {
    println(Solve03_1(325489))
    println(Solve03_2(325489))
}