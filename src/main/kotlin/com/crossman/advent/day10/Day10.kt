package com.crossman.advent.day10

import com.crossman.advent.Utils.readLines
import java.util.concurrent.atomic.AtomicInteger

sealed interface Pipe {
    val id: Int

    fun hasNorth(): Boolean
    fun hasSouth(): Boolean
    fun hasWest(): Boolean
    fun hasEast(): Boolean

    fun getAdjacentPipes(): List<Pipe>
}

data class Open(override val id: Int, val shape: Char, var north: Pipe?, var south: Pipe?, var east: Pipe?, var west: Pipe?) : Pipe {
    override fun hasNorth(): Boolean {
        return shape == '|' || shape == 'L' || shape == 'J'
    }

    override fun hasSouth(): Boolean {
        return shape == '|' || shape == '7' || shape == 'F'
    }

    override fun hasWest(): Boolean {
        return shape == '-' || shape == '7' || shape == 'J'
    }

    override fun hasEast(): Boolean {
        return shape == '-' || shape == 'L' || shape == 'F'
    }

    override fun getAdjacentPipes(): List<Pipe> {
        val next = ArrayList<Pipe>()
        if (north != null) {
            next.add(north!!)
        }
        if (south != null) {
            next.add(south!!)
        }
        if (west != null) {
            next.add(west!!)
        }
        if (east != null) {
            next.add(east!!)
        }
        return next
    }

    override fun toString(): String {
        return shape.toString()
    }
}

data object Ground : Pipe {
    override val id: Int
        get() = -1

    override fun hasNorth(): Boolean {
        return false
    }

    override fun hasSouth(): Boolean {
        return false
    }

    override fun hasWest(): Boolean {
        return false
    }

    override fun hasEast(): Boolean {
        return false
    }

    override fun getAdjacentPipes(): List<Pipe> {
        return listOf()
    }
}

object Day10a {
    fun execute(path: String): Int {
        val lastId = AtomicInteger(0)
        val sourceId = AtomicInteger()
        val idToCell = mutableMapOf<Int,Pipe>()
        val pipes = readLines(path).map { line ->
            line.toList().map { symbol ->
                val nextId = lastId.get() + 1
                lastId.set(nextId)

                if (symbol == 'S') {
                    sourceId.set(nextId)
                }
                val cell = when(symbol) {
                    '.' -> Ground
                    else -> Open(nextId, symbol, null, null, null, null)
                }
                idToCell[nextId] = cell
                cell
            }
        }
        pipes.forEachIndexed { iRow, row ->
            row.forEachIndexed { iCol, cell ->
                if (cell is Open) {
                    var otherCell: Pipe? = null
                    if (cell.shape == 'S') {
                        otherCell = pipes.getOrElse(iRow - 1) { listOf() }.getOrElse(iCol) { Ground }
                        if (otherCell.hasSouth()) {
                            cell.north = otherCell
                        }

                        otherCell = pipes.getOrElse(iRow + 1) { listOf() }.getOrElse(iCol) { Ground }
                        if (otherCell.hasNorth()) {
                            cell.south = otherCell
                        }

                        otherCell = pipes[iRow].getOrElse(iCol - 1) { Ground }
                        if (otherCell.hasEast()) {
                            cell.west = otherCell
                        }

                        otherCell = pipes[iRow].getOrElse(iCol + 1) { Ground }
                        if (otherCell.hasWest()) {
                            cell.east = otherCell
                        }
                    } else {
                        if (iRow > 0 && cell.hasNorth()) {
                            cell.north = pipes[iRow - 1][iCol]
                        } else {
                            cell.north = Ground
                        }
                        if (iRow < pipes.size - 1 && cell.hasSouth()) {
                            cell.south = pipes[iRow + 1][iCol]
                        } else {
                            cell.south = Ground
                        }
                        if (iCol > 0 && cell.hasWest()) {
                            cell.west = pipes[iRow][iCol - 1]
                        } else {
                            cell.west = Ground
                        }
                        if (iCol < row.size - 1 && cell.hasEast()) {
                            cell.east = pipes[iRow][iCol + 1]
                        } else {
                            cell.east = Ground
                        }
                    }
                }
            }
        }
        val sourcePos = sourceId.get()
        val source = idToCell[sourcePos]!!

        val distances = bfs(source)
        //println(distances)

        return distances.values.max()
    }

    private fun bfs(source: Pipe): Map<Int,Int> {
        val distances = mutableMapOf<Int,Int>()
        distances[source.id] = 0

        val queue = ArrayDeque<Pipe>()
        val explored = HashSet<Int>()
        explored.add(source.id)

        queue.add(source)

        while (queue.isNotEmpty()) {
            val next = queue.removeFirst()
            next.getAdjacentPipes().forEach { pipe ->
                val distance = distances[next.id]!! + 1

                if (!explored.contains(pipe.id)) {
                    explored.add(pipe.id)
                    distances[pipe.id] = distance
                    queue.add(pipe)
                }
            }
        }

        return distances
    }
}

object Day10b {
    fun execute(path: String): Int {
        return -1
    }
}

fun main() {
    //println(Day10a.execute("day10/sample01.txt"))
    //println(Day10a.execute("day10/sample02.txt"))
    println(Day10a.execute("day10/problem01.txt"))
}