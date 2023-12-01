package com.crossman.advent.day01

import com.crossman.advent.Utils

object Day01a {
    fun execute(path: String): Int {
        return Utils.readLines(path).map { line ->
            val first = requireNotNull(line.firstNumber())
            val last = requireNotNull(line.lastNumber())
            first * 10 + last
        }.reduce(Int::plus)
    }

    private fun String.firstNumber(): Int? {
        val len = this.length
        var i = 0
        while (i < len) {
            if (this[i].isDigit()) {
                return this[i].code - '0'.code
            }
            i += 1
        }
        return null
    }

    private fun String.lastNumber(): Int? {
        val len = this.length
        var i = len - 1
        while (i >= 0) {
            if (this[i].isDigit()) {
                return this[i].code - '0'.code
            }
            i -= 1
        }
        return null
    }
}

fun main() {
    //println(Day01a.execute("./day01/sample01.txt"))
    println(Day01a.execute("./day01/problem01.txt"))
}