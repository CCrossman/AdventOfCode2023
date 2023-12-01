package com.crossman.advent

import com.crossman.advent.Utils.readColumns
import com.crossman.advent.Utils.readLines
import java.io.BufferedReader
import java.io.InputStreamReader

object Utils {
    private val loader = ClassLoader.getSystemClassLoader()

    fun readLines(path: String): List<String> {
        val reader = loader.getResourceAsStream(path)?.let { InputStreamReader(it) }?.let { BufferedReader(it) }
        return reader.use { r ->
            r?.readLines() ?: listOf()
        }
    }

    fun readColumns(path: String, sep: Char): List<List<String>> {
        return readLines(path).map { it.split(sep) }
    }
}

fun main() {
    readColumns("test.txt", ' ').forEach {println(it)}
}