import java.io.File
import java.io.InputStream
import kotlin.math.sign

class TreeNode(name: String, weight: Int, children: List<String>?) {
    val Name = name
    val Weight = weight
    val Children = children
    var Parent: TreeNode? = null
    var CalcWeight = 0
}

fun List<TreeNode>.FindRoot(): TreeNode {
    return this.filter { node -> node.Parent == null }.first()
}

fun List<TreeNode>.CalcWeights() {
    this.CalcWeights(this.FindRoot())
}

fun List<TreeNode>.CalcWeights(node: TreeNode) {
    node.CalcWeight = node.Weight
    this.GetChildrenNodesOf(node)
        .forEach { child ->
                this.CalcWeights(child!!)
                node.CalcWeight += child.CalcWeight
        }
}

fun List<TreeNode>.FindImbalancedNodeNewWeight(): Int {
    val imbalancedNode = this.FindImbalancedNode(this.FindRoot())
    val siblings = this.GetChildrenNodesOf(imbalancedNode.Parent!!).filter { node -> node != imbalancedNode }
    return imbalancedNode.Weight + (siblings.first().CalcWeight - imbalancedNode.CalcWeight)
}

fun List<TreeNode>.FindImbalancedNode(node: TreeNode): TreeNode {
    var children = this.GetChildrenNodesOf(node)
    var weights = children.map { child -> child.CalcWeight }.distinct()
    if(weights.size > 1) {
        val imbalancedWeight = weights.find { weight -> children.filter { child -> child.CalcWeight == weight }.size == 1 }
        return this.FindImbalancedNode(children.find { child->child.CalcWeight == imbalancedWeight }!!)
    }
    else {
        return node
    }
}

fun List<TreeNode>.GetChildrenNodesOf(node: TreeNode): List<TreeNode> {
    if (node.Children == null) return emptyList()
    return node.Children
            .map { name -> this.find { child -> child.Name == name } }
            .filterNotNull()
            .toList()
}

fun Solve07_1(nodes: List<TreeNode>): String {
    return nodes.FindRoot().Name
}

fun Solve07_2(nodes: List<TreeNode>): Int {
    nodes.CalcWeights()
    return nodes.FindImbalancedNodeNewWeight()
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

     /*val l = PopulateTree("pbga (66)\n" +
            "xhth (57)\n" +
            "ebii (61)\n" +
            "havc (66)\n" +
            "ktlj (57)\n" +
            "fwft (72) -> ktlj, cntj, xhth\n" +
            "qoyq (66)\n" +
            "padx (45) -> pbga, havc, qoyq\n" +
            "tknk (41) -> ugml, padx, fwft\n" +
            "jptl (61)\n" +
            "ugml (68) -> gyxo, ebii, jptl\n" +
            "gyxo (61)\n" +
            "cntj (57)")*/
    println(Solve07_1(l))
    println(Solve07_2(l))
}
