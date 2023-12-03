package com.crossman.advent

class Grid(private val lines: List<String>) {
    val height = lines.size
    val width = (lines.getOrElse(0) { "" }).length

    fun forEachWithIndexRowMajor(blk: (Int,Int,Char) -> Unit): Unit {
        for (y in 0 ..< height) {
            for (x in 0..< width) {
                val ch = get(x,y)
                blk(x,y,ch!!)
            }
        }
    }

    fun forEachWithIndexColMajor(blk: (Int,Int,Char) -> Unit): Unit {
        for (x in 0..< width) {
            for (y in 0 ..< height) {
                val ch = get(x,y)
                blk(x,y,ch!!)
            }
        }
    }

    fun get(x: Int, y: Int): Char? {
        if (x < 0 || x >= width) {
            return null
        }
        if (y < 0 || y >= height) {
            return null
        }
        return lines[y][x]
    }

    fun leftOf(x: Int, y: Int): Char? {
        return get(x - 1, y)
    }
}