const val JOKER = false

enum class Type (val value:Int){
    FiveOfAKind(value = 7),
    FourOfAKind(value = 6),
    FullHouse(value = 5),
    ThreeOfAKind(value = 4),
    TwoPair(value = 3),
    OnePair(value = 2),
    HighCard(value = 1)
}


fun String.type(): Int{
    val counts = this.groupingBy { it }.eachCount().values
    
    return when {
        5 in counts -> Type.FiveOfAKind.value
        4 in counts -> Type.FourOfAKind.value
        3 in counts && 2 in counts -> Type.FullHouse.value
        3 in counts -> Type.ThreeOfAKind.value
        2 in counts && 2 in (counts - 2) -> Type.TwoPair.value
        2 in counts -> Type.OnePair.value
        else -> Type.HighCard.value
    }
}

fun String.typeWithJokers(): Int{
    val baseStrength = this.filter { it != 'J' }.type()
    val upgrades = this.count {it == 'J'}

    return (1..upgrades).fold(baseStrength) {strength: Int, _ :Int->
        when(strength) {
            Type.FiveOfAKind.value -> Type.FiveOfAKind.value
            Type.FourOfAKind.value -> Type.FiveOfAKind.value
            Type.FullHouse.value -> Type.FourOfAKind.value
            Type.ThreeOfAKind.value -> Type.FourOfAKind.value
            Type.TwoPair.value -> Type.FullHouse.value
            Type.OnePair.value -> Type.ThreeOfAKind.value
            Type.HighCard.value -> Type.OnePair.value
            else -> error("This should never happen")
        }
    }
}

data class Hand(val cards: String, val bid: Int)

fun parseHands(input: List<String>) = input
    .map { it.split(" ") }
    .map { (cards: String, bid:String) -> Hand(cards, bid.toInt()) }

fun solve(
    alphabet: String,
    input: List<String>,
    ranks: String,
    type: (card:String) -> Int
): Int{
    fun String.alphabetize() = map {
        alphabet[ranks.indexOf(it)]
    }.joinToString("")
    
    return parseHands(input)
        .sortedWith(compareBy({type(it.cards)}, {it.cards.alphabetize()}))
        .mapIndexed{index, (_, bid) -> bid * (index + 1)}
        .sum()
}


fun main() {
    fun part1(input: List<String>): Int =
        solve(
            input = input,
            alphabet = "abcdefghijklm",
            ranks = "23456789TJQKA",
            type = {hand -> hand.type()}
        )

    fun part2(input: List<String>): Int =
        solve(
            input = input,
            alphabet = "abcdefghijklm",
            ranks = "J23456789TQKA",
            type = { it.typeWithJokers()}
        )
    // test if implementation meets criteria from the description, like:
    //val testInput = readInput("Day07_test")
    //check(part1(testInput) == 1)

    val input = readInput("Day07")

    part1(input).println()
    part2(input).println()
}