import java.io.File
import java.io.InputStream

class Memory:HashMap<String, Int> (){
    private var maxEver = Int.MIN_VALUE

    fun ReadRegister(register: String): Int {
        if(this.containsKey(register)) return this[register]!!

        this[register] = 0
        return 0
    }

    fun SetRegister(register: String, value: Int) {
        if(value > maxEver)  maxEver = value
        this[register] = value
    }

    fun AddToRegister(register: String, value: Int) {
        SetRegister(register, ReadRegister(register) + value)
    }

    fun GetMax(): Int {
        return this.values.filterNotNull().max() ?: 0
    }

    fun GetMaxEver(): Int {
        return maxEver
    }
}

fun RunCode(program: String): Memory {
    var memory = Memory()

    val lines = program.trim().split("\n").map { s -> s.trim() }

    for(line in lines) {
        val tokens = line.split(" ")
        val storeRegister = tokens[0]
        val compareOperand1 = memory.ReadRegister(tokens[4])
        val compareOperator = tokens[5]
        val compareOperand2 = tokens[6].toInt()
        val compareResult = when(compareOperator){
            ">" -> compareOperand1 > compareOperand2
            "<" -> compareOperand1 < compareOperand2
            "==" ->compareOperand1 == compareOperand2
            "!=" ->compareOperand1 != compareOperand2
            ">=" ->compareOperand1 >= compareOperand2
            "<=" -> compareOperand1 <= compareOperand2
            else -> false
        }
        if(compareResult) {
            val storeValue = tokens[2].toInt()
            val sign = if(tokens[1] == "inc") 1 else -1
            memory.AddToRegister(storeRegister,  sign * storeValue)
        }
    }

    return memory
}

fun LoadData08(): String {
    val inputStream: InputStream = File("data/day08.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    return inputString
}

fun Solve08_1(memory: Memory): Int {
    return memory.GetMax()
}

fun Solve08_2(memory: Memory): Int {
    return memory.GetMaxEver()
}

fun main(args: Array<String>) {
    val program1 = "b inc 5 if a > 1\n" +
            "a inc 1 if b < 5\n" +
            "c dec -10 if a >= 1\n" +
            "c inc -20 if c == 10"
    val program2 = LoadData08()
    val memory = RunCode(program2)
    println(Solve08_1(memory))
    println(Solve08_2(memory))
}