fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    //val testInput = readInput("Day08_test")
    //check(part1(testInput) == 1)

    val input = readInput("Day08")
    val instructions: String = readInput("Day08_Instructions")[0]
    
    val list = parseInput(input)
    solve(list, instructions)
    //part1(input).println()
    //part2(input).println()
}

fun solve(input: List<Route>, instructions: String) :Int{
    var currentNode: String = input[0].node
    var routesTaken = 0
    val stringBuilder = StringBuilder(instructions)
    
    for (instruction in stringBuilder){
        if(currentNode != "ZZZ"){
            if(instruction == 'L' && stringBuilder.isNotEmpty()){
                val currentRoute = input.find { it.node == currentNode }
                if(currentRoute != null){
                    currentNode = currentRoute.leftNav
                } else throw error("Keine Route gefunden")
            }
            if(instruction == 'R' && stringBuilder.isNotEmpty()){
                val currentRoute = input.find { it.node == currentNode }
                if(currentRoute != null){
                    currentNode = currentRoute.rightNav
                } else throw error("Keine Route gefunden")
            }
            else if(stringBuilder.isEmpty()){
                stringBuilder.append(instructions)
            }
            stringBuilder.deleteAt(0)
            routesTaken ++
        }
        
    }
    return 0
}

fun parseInput(input: List<String>) = input
    .map { it.replace(" ", "").replace("(", "").replace(")", "") }
    .map { it.split("=", ",") }
    .map { (node:String, leftNav:String, rightNav:String) -> Route(node, leftNav, rightNav) }

data class Route(val node:String, val leftNav:String, val rightNav:String)
//Liste in 3 elemente aufteilen
