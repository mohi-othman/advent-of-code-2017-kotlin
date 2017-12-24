class DuetMachine(val program0: Program?, val program1: Program?) {
    enum class Status {
        Running,
        Terminated,
        Awaiting
    }

    class OpResult (val status:Status, val output: Long? = null)

    class RunResult (val opCount: Int, val output: List<Long>)

    class Program(id: Long, code: String = "", val solo: Boolean = true) {
        var lines = listOf<String>()
        var memory = hashMapOf<String, Long>()
        var queue = mutableListOf<Long>()
        var step = 0L
        var sendCount = 0

        init {
            lines = code.split("\n")
            if(!solo) memory["p"] = id
        }

        fun runUntilStop(): RunResult {
            var output = mutableListOf<Long>()
            var count = 0
            while(true){
                val opResult = runOp()
                if(opResult.status == Status.Running) count++
                if(opResult.status == Status.Running && opResult.output != null) {
                    output.add(opResult.output)
                }
                else if(opResult.status != Status.Running)
                    break
            }
            return RunResult(count, output.toList())
        }

        fun runOp(): OpResult {
            if(step > lines.count() - 1) {
                return OpResult(Status.Terminated, null)
            }
            val args = lines[step.toInt()].split(" ").map { it.trim().toLowerCase() }
            step++
            when(args[0]) {
                "snd" -> if(solo) {
                            queue.add(getValue(args[1]))
                        } else {
                            sendCount++
                            return OpResult(Status.Running, getValue(args[1]))
                        }
                "set" -> memory[args[1]] = getValue(args[2])
                "add" -> memory[args[1]] = getValue(args[1]) + getValue(args[2])
                "mul" -> memory[args[1]] = getValue(args[1]) * getValue(args[2])
                "mod" -> memory[args[1]] = getValue(args[1]) % getValue(args[2])
                "rcv" -> if(solo && getValue(args[1]) != 0L) {
                            return OpResult(Status.Terminated, queue.last())
                        }
                        else if(!solo) {
                            if(queue.size > 0) {
                                memory[args[1]] = queue.first()
                                queue.removeAt(0)
                            } else {
                                step--
                                return OpResult(Status.Awaiting)
                            }
                        }
                "jgz" -> if(getValue(args[1]) > 0) {
                    step += getValue(args[2]) - 1
                }
            }
            return OpResult(Status.Running)
        }

        fun addToQueue(value: Long) {
            queue.add(value)
        }

        fun getValue(arg: String): Long {
            var result = arg.toLongOrNull()

            if(result != null) {
                return result
            }
            else {
                return memory.getOrPut(arg, { 0 })
            }
        }
    }

    fun runSolo(): Long {
        if(program0 == null) {
            throw Exception("Program 0 not initialized")
        } else {
            program0.runUntilStop()
            return program0.queue.last()
        }
    }

    fun runDual(): Int {
        if(program0 == null || program1 == null)
            throw Exception("Programs not initialized")

        while(true) {
            val result0 = program0.runUntilStop()

            for(item in result0.output) {
                program1.addToQueue(item)
            }

            val result1 = program1.runUntilStop()

            if(result0.opCount == 0 && result1.opCount == 0) {
                return program1.sendCount
            }

            for(item in result1.output) {
                program0.addToQueue(item)
            }
        }
    }
}

val puzzle18Program = "set i 31\n" +
        "set a 1\n" +
        "mul p 17\n" +
        "jgz p p\n" +
        "mul a 2\n" +
        "add i -1\n" +
        "jgz i -2\n" +
        "add a -1\n" +
        "set i 127\n" +
        "set p 680\n" +
        "mul p 8505\n" +
        "mod p a\n" +
        "mul p 129749\n" +
        "add p 12345\n" +
        "mod p a\n" +
        "set b p\n" +
        "mod b 10000\n" +
        "snd b\n" +
        "add i -1\n" +
        "jgz i -9\n" +
        "jgz a 3\n" +
        "rcv b\n" +
        "jgz b -1\n" +
        "set f 0\n" +
        "set i 126\n" +
        "rcv a\n" +
        "rcv b\n" +
        "set p a\n" +
        "mul p -1\n" +
        "add p b\n" +
        "jgz p 4\n" +
        "snd a\n" +
        "set a b\n" +
        "jgz 1 3\n" +
        "snd b\n" +
        "set f 1\n" +
        "add i -1\n" +
        "jgz i -11\n" +
        "snd a\n" +
        "jgz f -16\n" +
        "jgz a -19"

fun Test18_1(): Boolean {
    val program0 = DuetMachine.Program(0,
            "set a 1\n" +
                    "add a 2\n" +
                    "mul a a\n" +
                    "mod a 5\n" +
                    "snd a\n" +
                    "set a 0\n" +
                    "rcv a\n" +
                    "jgz a -1\n" +
                    "set a 1\n" +
                    "jgz a -2",
            true)
    var machine = DuetMachine(program0, null)
    return machine.runSolo() == 4L
}

fun Test18_2(): Boolean {
    var code = "snd 1\n" +
            "snd 2\n" +
            "snd p\n" +
            "rcv a\n" +
            "rcv b\n" +
            "rcv c\n" +
            "rcv d"
    val program0 = DuetMachine.Program(0,
            code,
            false)
    val program1 = DuetMachine.Program(1,
            code,
            false)
    var machine = DuetMachine(program0, program1)
    var count = machine.runDual()
    return count == 3
}

fun Solve18_1(): Long {
    val program0 = DuetMachine.Program(0,
            puzzle18Program,
            true)
    var machine = DuetMachine(program0, null)
    return machine.runSolo()
}

fun Solve18_2(): Int {
    val program0 = DuetMachine.Program(0,
            puzzle18Program,
            false)
    val program1 = DuetMachine.Program(1,
            puzzle18Program,
            false)
    var machine = DuetMachine(program0, program1)
    return machine.runDual()
}

fun main(args: Array<String>) {
    println(Test18_1())
    println(Test18_2())
    println(Solve18_1())
    println(Solve18_2())
}