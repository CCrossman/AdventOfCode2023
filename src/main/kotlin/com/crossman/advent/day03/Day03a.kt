package com.crossman.advent.day03

import com.crossman.advent.Grid
import com.crossman.advent.Utils.readGrid

object Day03a {

    data class GridNumber(val x: Int, val y: Int, val value: String, val z: Int = x + value.length)

    fun execute(path: String): Int {
        var sum = 0
        val grid = readGrid(path)
        grid.forEachWithIndexRowMajor { x,y,ch ->
            if (ch.isDigit() && grid.leftOf(x,y).isNotDigit()) {
                val num = getNumberAt(grid,x,y)
                val touchesSymbol = isTouchingSymbol(grid,num)
                if (touchesSymbol) {
                    sum += num.value.toInt()
                }
            }
        }
        return sum
    }

    private fun isTouchingSymbol(grid: Grid, n: GridNumber): Boolean {
        for (x in (n.x - 1) .. (n.z)) {
            for (y in (n.y - 1) .. (n.y + 1)) {
                val ch = grid.get(x,y)
                if (ch != null && ch.isNotDigit() && ch != '.') {
                    return true
                }
            }
        }
        return false
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
//    println(Day03a.execute("day03/sample01.txt"))
    println(Day03a.execute("day03/problem01.txt"))
}