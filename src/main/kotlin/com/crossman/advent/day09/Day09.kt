package com.crossman.advent.day09

import com.crossman.advent.Utils.readLines

object Day09a {
    fun execute(path: String): Int {
        return readLines(path).sumOf { line ->
            val sequence = line.split(' ').map { it.toInt() }
            val nextSequence = findNextSequences(sequence, mutableListOf(sequence))
            extrapolateNextValue(nextSequence)
        }
    }

    private fun extrapolateNextValue(nextSequence: List<List<Int>>): Int {
        var value = 0
        var idx = nextSequence.size - 1
        while (idx >= 0) {
            val list = nextSequence[idx]
            value += list.last()
            idx -= 1
        }
        return value
    }

    private fun findNextSequences(sequence: List<Int>, out: List<List<Int>>): List<List<Int>> {
        val nextSequence = ArrayList<Int>()
        for (i in 0 ..< (sequence.size - 1)) {
            nextSequence.add(sequence[i+1] - sequence[i])
        }
        out.addLast(nextSequence)
        if (nextSequence.containsOnlyZeros()) {
            return out
        }
        return findNextSequences(nextSequence,out)
    }

    private fun List<Int>.containsOnlyZeros(): Boolean {
        val distinct = this.distinct()
        return distinct.size == 1 && distinct[0] == 0
    }
}

object Day09b {
    fun execute(path: String): Int {
        return readLines(path).sumOf { line ->
            val sequence = line.split(' ').map { it.toInt() }
            val nextSequence = findNextSequences(sequence, mutableListOf(sequence))
            extrapolatePrevValue(nextSequence)
        }
    }

    private fun extrapolatePrevValue(nextSequence: List<List<Int>>): Int {
        var value = 0
        var idx = nextSequence.size - 1
        while (idx >= 0) {
            val list = nextSequence[idx]
            value = list.first() - value
            idx -= 1
        }
        return value
    }

    private fun findNextSequences(sequence: List<Int>, out: List<List<Int>>): List<List<Int>> {
        val nextSequence = ArrayList<Int>()
        for (i in 0 ..< (sequence.size - 1)) {
            nextSequence.add(sequence[i+1] - sequence[i])
        }
        out.addLast(nextSequence)
        if (nextSequence.containsOnlyZeros()) {
            return out
        }
        return findNextSequences(nextSequence,out)
    }

    private fun List<Int>.containsOnlyZeros(): Boolean {
        val distinct = this.distinct()
        return distinct.size == 1 && distinct[0] == 0
    }
}

fun main() {
    //println(Day09a.execute("day09/sample01.txt"))
    //println(Day09a.execute("day09/problem01.txt"))
    //println(Day09b.execute("day09/sample01.txt"))
    println(Day09b.execute("day09/problem01.txt"))
}