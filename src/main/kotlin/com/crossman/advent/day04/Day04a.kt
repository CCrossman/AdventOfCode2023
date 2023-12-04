package com.crossman.advent.day04

import com.crossman.advent.Utils.readLines

object Day04a {
    data class Game(val id: String, val myNumbers: List<Int>, val winningNumbers: List<Int>) {
        fun getScore(): Int {
            val count = myNumbers.count { winningNumbers.contains(it) }
            return Math.pow(2.0, (count - 1.0)).toInt()
        }
    }

    fun execute(path: String): Int {
        val games = readLines(path).map { line ->
            val (id, numbers) = line.split(":")
            val (winningNumberString, ownedNumberString) = numbers.split("|")
            val winningNumbers = winningNumberString.split(' ').filter { it.isNotBlank() }.map { it.trim().toInt() }
            val ownedNumbers = ownedNumberString.split(' ').filter { it.isNotBlank() }.map { it.trim().toInt() }
            Game(id, ownedNumbers, winningNumbers)
        }
        return games.sumOf { game -> game.getScore() }
    }
}

fun main() {
    //println(Day04a.execute("day04/sample01.txt"))
    println(Day04a.execute("day04/problem01.txt"))
}