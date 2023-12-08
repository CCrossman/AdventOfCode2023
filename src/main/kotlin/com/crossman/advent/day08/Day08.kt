package com.crossman.advent.day08

import com.crossman.advent.Utils.getReader
import java.lang.IllegalArgumentException

class Node(val key: String, var left: Node?, var right: Node?) {
    override fun toString(): String {
        return "Node($key, ${left?.key}, ${right?.key})"
    }
}

object Day08a {
    fun execute(path: String): Int {
        getReader(path)?.use {
            val directions = it.readLine()
            it.readLine() // empty line

            val network = HashMap<String,Node>()

            var line = it.readLine()
            while (line != null && line.isNotBlank()) {
                val (key,pair) = line.split('=').map { it.trim() }
                val (left,right) = pair.substring(1,pair.length-1).split(',').map { it.trim() }

                val node = network.computeIfAbsent(key) { Node(key, null, null) }
                node.left = network.computeIfAbsent(left) { Node(left, null, null) }
                node.right = network.computeIfAbsent(right) { Node(right, null, null) }

                // next line
                line = it.readLine()
            }

            var i = 0
            var n: Node = network["AAA"]!!
            while (n.key != "ZZZ") {
                val path = directions[i % directions.length]
                if (path == 'L') {
                    n = n.left!!
                } else if (path == 'R') {
                    n = n.right!!
                } else {
                    throw IllegalArgumentException("unexpected path")
                }
                i += 1
            }

            return i
        }
        return -1
    }
}

object Day08b {
    fun execute(path: String): Int {
        return -1
    }
}

fun main() {
    //println(Day08a.execute("day08/sample01.txt"))
    //println(Day08a.execute("day08/sample02.txt"))
    println(Day08a.execute("day08/problem01.txt"))
}