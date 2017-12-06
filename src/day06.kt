data class Day06Result(val cycles: Int, val loopSize: Int)

fun Solve06Universal(input: String): Day06Result {
    var state = input.trim().split("\t").map { x -> x.trim().toInt() }.toMutableList()

    if (state.size == 0)
        return Day06Result(0, 0)

    var stateSet = mutableListOf(state.toList())
    var result = 0
    while (true) {
        result++
        var qty = state.max()!!
        var idx = state.indexOf(qty)
        state[idx] = 0
        while (qty > 0) {
            idx = (idx + 1) % state.size
            state[idx] += 1
            qty--
        }
        if (state in stateSet)
            return Day06Result(cycles = result, loopSize = result - stateSet.indexOf(state))
        stateSet.add(state.toList())
    }
}

fun Solve06_1(input: String): Int {
    return Solve06Universal(input).cycles
}

fun Solve06_2(input: String): Int {
    return Solve06Universal(input).loopSize
}

fun main(args: Array<String>) {
    val test1 = "0\t2\t7\t0"
    val test2 = "4\t10\t4\t1\t8\t4\t9\t14\t5\t1\t14\t15\t0\t15\t3\t5"
    println(Solve06_1(test2))
    println(Solve06_2(test2))
}