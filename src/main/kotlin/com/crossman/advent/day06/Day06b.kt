package com.crossman.advent.day06

import com.crossman.advent.Utils.readLines
import java.math.BigInteger

object Day06b {
    fun execute(path: String): Int {
        val m = readLines(path).associate { line ->
            val (header, rest) = line.split(':').map { it.trim() }
            val cell = rest.toList().filter { !it.isWhitespace() }.joinToString("").toBigInteger()
            header to cell
        }
        val time = m["Time"]!!
        val distance = m["Distance"]!!

        var waysToWin = 0
        var wait = BigInteger.ZERO
        while (wait < time) {
            val howFarMoved = getHowFarMoved(time,wait)
            if (howFarMoved > distance) {
                waysToWin += 1
            }
            wait = wait.plus(BigInteger.ONE)
        }
        return waysToWin
    }

    private fun getHowFarMoved(time: BigInteger, wait: BigInteger): BigInteger {
        val timeToMove = time - wait
        return wait.multiply(timeToMove)
    }
}

fun main() {
    //println(Day06b.execute("day06/sample01.txt"))
    println(Day06b.execute("day06/problem01.txt"))
}