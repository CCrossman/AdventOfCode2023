package com.crossman.advent.day07

import com.crossman.advent.Utils.readLines

data class Card(val face: Char) : Comparable<Card> {
    companion object {
        fun getRank(card: Card): Int {
            return "23456789TJQKA".indexOf(card.face)
        }
    }
    override fun compareTo(other: Card): Int {
        val thisRank = getRank(this)
        val thatRank = getRank(other)
        return thisRank - thatRank
    }
}

data class Hand(val cards: List<Card>) : Comparable<Hand> {
    companion object {
        private val FULL_HOUSE_COUNTS = listOf(2,3)

        fun getPrimaryRank(faceCount: Map<Char,Int>): Int {

            // five of a kind?
            if (faceCount.values.contains(5)) {
                return 7
            }

            // four of a kind?
            if (faceCount.values.contains(4)) {
                return 6
            }

            // full house?
            if (faceCount.values.sorted() == FULL_HOUSE_COUNTS) {
                return 5
            }

            // three of a kind?
            if (faceCount.values.contains(3)) {
                return 4
            }

            // two pair?
            if (faceCount.values.count { it == 2 } == 2) {
                return 3
            }

            // one pair?
            if (faceCount.values.contains(2)) {
                return 2
            }

            // high card?
            return 1
        }
    }

    override fun toString(): String {
        return cards.map { it.face }.joinToString("","Hand(",")")
    }

    override fun compareTo(other: Hand): Int {
        val thisCards = this.cards
        require(thisCards.size == 5)
        val thatCards = other.cards
        require(thatCards.size == 5)

        val thisFaceCount = thisCards.groupBy { it.face }.map { (face,list) -> face to list.size }.toMap()
        val thatFaceCount = thatCards.groupBy { it.face }.map { (face,list) -> face to list.size }.toMap()

        val thisPrimaryRank = getPrimaryRank(thisFaceCount)
        val thatPrimaryRank = getPrimaryRank(thatFaceCount)

        if (thisPrimaryRank > thatPrimaryRank) {
            return 1
        }
        if (thisPrimaryRank < thatPrimaryRank) {
            return -1
        }

        // now compare by faces
        for (i in 0 .. 4) {
            val thisCard = thisCards[i]
            val thatCard = thatCards[i]
            val comparison = thisCard.compareTo(thatCard)
            if (comparison != 0) {
                return comparison
            }
        }

        // same hands
        return 0
    }

}

object Day07a {

    fun execute(path: String): Int {
        val game = readLines(path).map { line ->
            val (cards,bet) = line.split(' ')
            val hand = Hand(cards.toList().map { Card(it) })
            hand to bet.toInt()
        }.sortedBy { it.first }

        val results = mutableMapOf<Hand,Int>()
        game.forEachIndexed { index,(hand,bet) ->
            results[hand] = bet * (index + 1)
        }
        return results.values.sum()
    }
}

data class JCard(val face: Char) : Comparable<JCard> {
    companion object {
        fun getRank(card: JCard): Int {
            return "J23456789TQKA".indexOf(card.face)
        }
    }
    override fun compareTo(other: JCard): Int {
        val thisRank = getRank(this)
        val thatRank = getRank(other)
        return thisRank - thatRank
    }
}data class JHand(val cards: List<JCard>) : Comparable<JHand> {
    companion object {
        private val FULL_HOUSE_COUNTS = listOf(2,3)

        fun getPrimaryRank(faceCount: Map<Char,Int>): Int {

            // five of a kind?
            if (isFiveOfAKind(faceCount)) {
                return 7
            }

            // four of a kind?
            if (isFourOfAKind(faceCount)) {
                return 6
            }

            // full house?
            if (isFullHouse(faceCount)) {
                return 5
            }

            // three of a kind?
            if (isThreeOfAKind(faceCount)) {
                return 4
            }

            // two pair?
            if (isTwoPair(faceCount)) {
                return 3
            }

            // one pair?
            if (isPair(faceCount)) {
                return 2
            }

            // high card?
            return 1
        }

        private fun isPair(faceCount: Map<Char, Int>): Boolean {
            val numJs = faceCount.getOrDefault('J',0)

            val faceCountWithoutJs = HashMap(faceCount)
            faceCountWithoutJs.remove('J')

            if (numJs == 0 && faceCountWithoutJs.values.contains(2)) {
                return true
            }
            if (numJs >= 1) {
                return true
            }
            return false
        }

        private fun isTwoPair(faceCount: Map<Char, Int>): Boolean {
            val numJs = faceCount.getOrDefault('J',0)

            val faceCountWithoutJs = HashMap(faceCount)
            faceCountWithoutJs.remove('J')

            if (numJs == 0 && faceCountWithoutJs.values.count { it == 2 } == 2) {
                return true
            }
            if (numJs == 1 && faceCountWithoutJs.values.contains(1) && faceCountWithoutJs.values.contains(2)) {
                return true
            }
            if (numJs == 2 && faceCountWithoutJs.values.contains(2) || (faceCountWithoutJs.values.count { it == 1} == 2)) {
                return true
            }
            if (numJs >= 3) {
                return true
            }
            return false
        }

        private fun isThreeOfAKind(faceCount: Map<Char, Int>): Boolean {
            val numJs = faceCount.getOrDefault('J',0)

            val faceCountWithoutJs = HashMap(faceCount)
            faceCountWithoutJs.remove('J')

            if (numJs == 0 && faceCountWithoutJs.values.contains(3)) {
                return true
            }
            if (numJs == 1 && faceCountWithoutJs.values.contains(2)) {
                return true
            }
            if (numJs == 2 && faceCountWithoutJs.values.contains(1)) {
                return true
            }
            if (numJs >= 3) {
                return true
            }
            return false
        }

        private fun isFullHouse(faceCount: Map<Char, Int>): Boolean {
            if (faceCount.values.sorted() == FULL_HOUSE_COUNTS) {
                return true
            }
            val numJs = faceCount.getOrDefault('J',0)
            if (numJs > 0) {
                val faceCountWithoutJs = HashMap(faceCount)
                faceCountWithoutJs.remove('J')

                val valuesSorted = faceCountWithoutJs.values.sorted()
                if (numJs == 1) {
                    return valuesSorted == listOf(1,3) || valuesSorted == listOf(2,2)
                }
                if (numJs == 2) {
                    return valuesSorted == listOf(3) || valuesSorted == listOf(1,2)
                }
                if (numJs == 3) {
                    return valuesSorted == listOf(2) || valuesSorted == listOf(1,1)
                }
                if (numJs == 4) {
                    return valuesSorted == listOf(1)
                }
                return true
            }
            return false
        }

        private fun isFourOfAKind(faceCount: Map<Char, Int>): Boolean {
            if (faceCount.values.contains(4)) {
                return true
            }
            for (face in faceCount.keys) {
                if (face != 'J') {
                    if (faceCount.getOrDefault('J',0) + faceCount[face]!! == 4) {
                        return true
                    }
                }
            }
            return false
        }

        private fun isFiveOfAKind(faceCount: Map<Char, Int>): Boolean {
            if (faceCount.values.contains(5)) {
                return true
            }
            if (faceCount.size == 2 && faceCount.keys.contains('J')) {
                return true
            }
            return false
        }
    }

    override fun toString(): String {
        return cards.map { it.face }.joinToString("","Hand(",")")
    }

    override fun compareTo(other: JHand): Int {
        val thisCards = this.cards
        require(thisCards.size == 5)
        val thatCards = other.cards
        require(thatCards.size == 5)

        val thisFaceCount = getThisFaceCountFromJCards(thisCards)
        val thatFaceCount = getThisFaceCountFromJCards(thatCards)

        val thisPrimaryRank = getPrimaryRank(thisFaceCount)
        val thatPrimaryRank = getPrimaryRank(thatFaceCount)

        if (thisPrimaryRank > thatPrimaryRank) {
            return 1
        }
        if (thisPrimaryRank < thatPrimaryRank) {
            return -1
        }

        // now compare by faces
        for (i in 0 .. 4) {
            val thisCard = thisCards[i]
            val thatCard = thatCards[i]
            val comparison = thisCard.compareTo(thatCard)
            if (comparison != 0) {
                return comparison
            }
        }

        // same hands
        return 0
    }

    private fun getThisFaceCount(thisCards: List<Card>): Map<Char,Int> {
        return thisCards.groupBy { it.face }.map { (face, list) -> face to list.size }.toMap()
    }

    private fun getThisFaceCountFromJCards(thisCards: List<JCard>): Map<Char,Int> {
        return thisCards.groupBy { it.face }.map { (face, list) -> face to list.size }.toMap()
    }

}



object Day07b {

    fun execute(path: String): Int {
        val game = readLines(path).map { line ->
            val (cards,bet) = line.split(' ')
            val hand = JHand(cards.toList().map { JCard(it) })
            hand to bet.toInt()
        }.sortedBy { it.first }

        val results = mutableMapOf<JHand,Int>()
        game.forEachIndexed { index,(hand,bet) ->
            results[hand] = bet * (index + 1)
        }
        return results.values.sum()
    }
}

fun main() {
    //println(Day07a.execute("day07/sample01.txt"))
//    println(Day07a.execute("day07/problem01.txt"))
    //println(Day07b.execute("day07/sample01.txt"))
    println(Day07b.execute("day07/problem01.txt"))
}