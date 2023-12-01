package com.crossman.advent.day01

import com.crossman.advent.Utils.readLines

object Day01b {
    fun execute(path: String): Int {
        return readLines(path).map { line ->
            val first = requireNotNull(line.firstNumber())
            val last = requireNotNull(line.lastNumber())
            first * 10 + last
        }.reduce(Int::plus)
    }

    private val words = mapOf("one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9)

    private fun String.firstNumber(): Int? {
        val len = this.length
        var i = 0
        while (i < len) {
            val str = this.substring(i)
            for (word in words.keys) {
                if (str.startsWith(word)) {
                    return words[word]
                }
            }
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
            val str = this.substring(i)
            for (word in words.keys) {
                if (str.startsWith(word)) {
                    return words[word]
                }
            }
            if (this[i].isDigit()) {
                return this[i].code - '0'.code
            }
            i -= 1
        }
        return null
    }
}

fun main() {
    //println(Day01b.execute("day01/sample02.txt"))
    println(Day01b.execute("day01/problem02.txt"))
}