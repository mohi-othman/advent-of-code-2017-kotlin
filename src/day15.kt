import kotlin.system.measureTimeMillis

val DAY15_CONST = 2147483647
val DAY15_GEN_A_FACTOR = 16807
val DAY15_GEN_B_FACTOR = 48271

fun getGeneratorNextValue(prevValue: Long, factor: Int): Long {
    return (prevValue * factor) % DAY15_CONST
}

fun Long.getLower16(): String {
    val binary = this.toString(2)
    if (binary.length <= 16) return binary
    return binary.substring(binary.length - 16)
}

fun getGeneratorsJudgeCount(genAValue: Long, genBValue: Long, iterations: Int): Int {
    var result = 0
    var prevA = genAValue
    var prevB = genBValue
    for(i in 0 until iterations) {
        prevA = getGeneratorNextValue(prevA, DAY15_GEN_A_FACTOR)
        prevB = getGeneratorNextValue(prevB, DAY15_GEN_B_FACTOR)
        if(prevA.getLower16()==prevB.getLower16()) result++
    }
    return result
}

fun getCuratedGeneratorsJudgeCount(genAValue: Long, genBValue: Long, iterations: Int): Int {
    var result = 0
    var prevA = genAValue
    var prevB = genBValue
    for(i in 0 until iterations) {
        while(true) {
            prevA = getGeneratorNextValue(prevA, DAY15_GEN_A_FACTOR)
            if(prevA % 4 == 0L) {
                break
            }
        }
        while(true) {
            prevB = getGeneratorNextValue(prevB, DAY15_GEN_B_FACTOR)
            if(prevB % 8 == 0L) {
                break
            }
        }
        if(prevA.getLower16()==prevB.getLower16()) result++
    }
    return result
}

fun Test15_1():Boolean {
    return getGeneratorsJudgeCount(65, 8921, 40000000) == 588
}

fun Test15_2():Boolean {
    return getCuratedGeneratorsJudgeCount(65, 8921, 5000000) == 309
}

fun Solve15_1():Int {
    return getGeneratorsJudgeCount(883, 879, 40000000)
}

fun Solve15_2():Int {
    return getCuratedGeneratorsJudgeCount(883, 879, 5000000)
}

fun main(args: Array<String>) {
    //println(Test15_1())
    println(Test15_2())
    //println(Solve15_1())
    println(Solve15_2())
}