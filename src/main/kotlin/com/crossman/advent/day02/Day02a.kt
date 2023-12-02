package com.crossman.advent.day02

import com.crossman.advent.Utils.readLines

object Day02a {

    fun execute(path: String, numRed: Int, numGreen: Int, numBlue: Int): Int {
        val games = readLines(path).map { line ->
            val (game,rest) = line.split(":").map { it.trim() }
            val cubes = rest.split(";").map { it.trim().split(",").map { it.trim() }.map { phrase ->
                val (num,color) = phrase.split(' ').map { it.trim() }
                Cube(num.toInt(), Color.valueOf(color.uppercase()))
            } }

            val maximums = HashMap<Color,Int>()
            cubes.forEach { list ->
                list.forEach { cube ->
                    val curMax = maximums[cube.color] ?: Integer.MIN_VALUE
                    if (curMax < cube.count) {
                        maximums[cube.color] = cube.count
                    }
                }
            }

            val possible = (maximums[Color.RED] ?: 0) <= numRed
                    && (maximums[Color.GREEN] ?: 0) <= numGreen
                    && (maximums[Color.BLUE] ?: 0) <= numBlue

            game.substring("Game ".length).toInt() to possible
        }
        return games.map { (k,v) -> if (v) k else 0 }.reduce(Int::plus)
    }
}

fun main() {
    //println(Day02a.execute("day02/sample01.txt", 12, 13, 14))
    println(Day02a.execute("day02/problem01.txt", 12, 13, 14))
}