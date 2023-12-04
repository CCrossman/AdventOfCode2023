package com.crossman.advent.day04

import com.crossman.advent.Utils.readLines

object Day04b {
    data class Game(val id: Int, val myNumbers: List<Int>, val winningNumbers: List<Int>) {
        fun getScore(): Int {
            val count = countWinners()
            return Math.pow(2.0, (count - 1.0)).toInt()
        }

        fun countWinners() = myNumbers.count { winningNumbers.contains(it) }
    }

    fun execute(path: String): Int {
        val games = readLines(path).map { line ->
            val (id, numbers) = line.split(":")
            val (winningNumberString, ownedNumberString) = numbers.split("|")
            val winningNumbers = winningNumberString.split(' ').filter { it.isNotBlank() }.map { it.trim().toInt() }
            val ownedNumbers = ownedNumberString.split(' ').filter { it.isNotBlank() }.map { it.trim().toInt() }
            val idNum = id.substring("Game ".length).trim().toInt()
            Game(idNum, ownedNumbers, winningNumbers)
        }
        val cards = mutableMapOf<Int,Int>()

        games.forEach { game ->
            val id = game.id
            cards[id] = cards.getOrDefault(id, 0) + 1

            val delta = cards.getOrDefault(id, 0)

            for (i in 1 .. game.countWinners()) {
                val oldScore = cards.getOrDefault(id + i, 0)
                cards[id + i] = oldScore + delta
            }
        }

        return cards.values.fold(0) { sum, i -> sum + i }
    }
}

fun main() {
//    println(Day04b.execute("day04/sample01.txt"))
    println(Day04b.execute("day04/problem01.txt"))
}