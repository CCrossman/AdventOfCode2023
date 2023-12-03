package com.crossman.advent.day03

import com.crossman.advent.Grid
import com.crossman.advent.Utils.readGrid

object Day03b {

    data class GridNumber(val x: Int, val y: Int, val value: String, val z: Int = x + value.length) {

        fun touches(x2: Int, y2: Int): Boolean {
            return x2 in (x-1 .. z) && y2 in (y-1 .. y+1)
        }
    }

    fun execute(path: String): Int {
        var sum = 0
        val grid = readGrid(path)
        val numbers = ArrayList<GridNumber>()
        val gears = ArrayList<Pair<Int,Int>>()
        grid.forEachWithIndexRowMajor { x,y,ch ->
            if (ch.isDigit() && grid.leftOf(x,y).isNotDigit()) {
                val num = getNumberAt(grid,x,y)
                numbers.add(num)
            }
            if (ch == '*') {
                gears.add(Pair(x,y))
            }
        }

        gears.forEach { p ->
            val (x,y) = p
            val adjacentNumbers = getAdjacentNumbers(numbers,x,y)
            if (adjacentNumbers.size == 2) {
                val a = adjacentNumbers[0].value.toInt()
                val b = adjacentNumbers[1].value.toInt()
                sum += (a * b)
            }
        }

        return sum
    }

    private fun getAdjacentNumbers(numbers: List<GridNumber>, x: Int, y: Int): List<GridNumber> {
        val result = ArrayList<GridNumber>()
        for (number in numbers) {
            if (number.touches(x,y)) {
                result.add(number)
            }
        }
        return result
    }

    private fun getNumberAt(grid: Grid, x: Int, y: Int): GridNumber {
        var str = ""
        var pos = x
        while (pos < grid.width && isDigit(grid.get(pos,y))) {
            str += grid.get(pos,y)
            pos += 1
        }
        return GridNumber(x,y,str)
    }

    private fun isDigit(ch: Char?): Boolean {
        return ch != null && ch.isDigit()
    }

    private fun Char?.isNotDigit(): Boolean {
        return this == null || !this.isDigit()
    }
}

fun main() {
//    println(Day03b.execute("day03/sample01.txt"))
    println(Day03b.execute("day03/problem01.txt"))
}