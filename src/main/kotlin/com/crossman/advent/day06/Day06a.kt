package com.crossman.advent.day06

import com.crossman.advent.Utils.readLines

object Day06a {
    fun execute(path: String): Int {
        val m = readLines(path).associate { line ->
            val (header, rest) = line.split(':').map { it.trim() }
            val cells = rest.split(' ').map { it.trim() }.filter { it.isNotBlank() }.map { it.toInt() }
            header to cells
        }
        val howManyWaysToWin = mutableMapOf<Int,Int>()
        for (i in 0 ..< m["Time"]!!.size) {
            val time = m["Time"]!![i]
            val distance = m["Distance"]!![i]

            var waysToWin = 0
            for (wait in 0 .. time) {
                val howFarMoved = getHowFarMoved(time,wait)
                if (howFarMoved > distance) {
                    waysToWin += 1
                }
            }
            howManyWaysToWin[i] = waysToWin
        }
        return howManyWaysToWin.values.reduce { acc, i -> acc * i }
    }

    private fun getHowFarMoved(time: Int, wait: Int): Int {
        val timeToMove = time - wait
        return wait * (timeToMove)
    }
}

fun main() {
    //println(Day06a.execute("day06/sample01.txt"))
    println(Day06a.execute("day06/problem01.txt"))
}