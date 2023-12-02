package com.crossman.advent.day02

import com.crossman.advent.Utils.readLines

object Day02b {

    fun execute(path: String): Int {
        val games = readLines(path).map { line ->
            val (game, rest) = line.split(":").map { it.trim() }
            val cubes = rest.split(";").map {
                it.trim().split(",").map { it.trim() }.map { phrase ->
                    val (num, color) = phrase.split(' ').map { it.trim() }
                    Cube(num.toInt(), Color.valueOf(color.uppercase()))
                }
            }

            val maximums = HashMap<Color, Int>()
            cubes.forEach { list ->
                list.forEach { cube ->
                    val curMax = maximums[cube.color] ?: Integer.MIN_VALUE
                    if (curMax < cube.count) {
                        maximums[cube.color] = cube.count
                    }
                }
            }

            maximums[Color.RED]!! * maximums[Color.BLUE]!! * maximums[Color.GREEN]!!
        }
        return games.reduce(Int::plus)
    }
}

fun main() {
//    println(Day02b.execute("day02/sample01.txt"))
    println(Day02b.execute("day02/problem01.txt"))
}