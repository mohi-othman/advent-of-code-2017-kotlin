import java.io.File
import java.io.InputStream

class TreeNode(name: String, weight: Int, children: List<String>?, parent: TreeNode? = null) {
    val Name = name
    val Weight = weight
    val Children = children
    var Parent = parent
}

fun Solve07_1(nodes: List<TreeNode>): String {
    return nodes.filter { node -> node.Parent == null }.first().Name
}

fun PopulateTree(input: String): List<TreeNode> {
    val nodeRegex = Regex("""(\w+)\s+\((\d+)\)(\s+->\s+([\w,\s]*))*""")
    val nodes = input.trim()
                    .split("\n")
                    .map{ line -> nodeRegex.find(line) }
                    .map { match ->  TreeNode(name=match?.groupValues?.get(1) ?: "",
                                            weight = match?.groupValues?.get(2)?.toInt() ?: 0,
                                            children = match?.groupValues?.get(4)
                                                    ?.split(",")
                                                    ?.map { child -> child.trim() }
                                                    ?.filter { name -> name.trim().isNotEmpty()}
                                                    ?.toList())
                }.toList()
    for(node in nodes) {
        if(node.Children != null)
            for(child in node.Children) {
                nodes.find { item -> item.Name == child }
                        ?.Parent = node
            }
    }
    return nodes
}

fun LoadData07(): String {
    val inputStream: InputStream = File("data/day07.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    return inputString
}

fun main(args: Array<String>) {
    val l = PopulateTree(LoadData07())
    println(Solve07_1(l))
}
