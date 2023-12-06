package com.crossman.advent.day05

import com.crossman.advent.Utils.getReader
import java.io.BufferedReader

object Day05a {
    class Mapping(map: Map<Int, String>) {
        val parsed = mutableListOf<Pair<LongRange, Long>>()

        init {
            map.forEach { (k, line) ->
                if (k != 0) {
                    val (dest, src, len) = line.split(' ').map { it.trim().toLong() }
                    val range = src ..< src + len
                    parsed.add(Pair(range, dest))
                }
            }
        }

        fun getDestination(source: Long): Long {
            for (pair in parsed) {
                val (range,dest) = pair
                if (range.contains(source)) {
                    return dest + (source - range.first)
                }
            }
            return source
        }

        override fun toString(): String {
            return "Mapping(parsed=$parsed)"
        }
    }

    data class Plan(
        val seeds: String,
        val seedToSoil: Mapping,
        val soilToFertilizer: Mapping,
        val fertilizerToWater: Mapping,
        val waterToLight: Mapping,
        val lightToTemperature: Mapping,
        val temperatureToHumidity: Mapping,
        val humidityToLocation: Mapping
    ) {
        val allSeeds = seeds.split(' ').map { it.trim().toLong() }
    }

    fun execute(path: String): Long {
        val reader = getReader(path)!!
        val plan = reader.use {
            val seeds = it.readLine().substring("seeds: ".length)

            // consume blank line
            it.readLine()

            val seedToSoil = it.readUntilBlank()
            val soilToFertilizer = it.readUntilBlank()
            val fertilizerToWater = it.readUntilBlank()
            val waterToLight = it.readUntilBlank()
            val lightToTemperature = it.readUntilBlank()
            val temperatureToHumidity = it.readUntilBlank()
            val humidityToLocation = it.readUntilBlank()

            Plan(
                seeds,
                Mapping(seedToSoil),
                Mapping(soilToFertilizer),
                Mapping(fertilizerToWater),
                Mapping(waterToLight),
                Mapping(lightToTemperature),
                Mapping(temperatureToHumidity),
                Mapping(humidityToLocation)
            )
        }

        val planted = plan.allSeeds.map { seed ->
            val soil = plan.seedToSoil.getDestination(seed)
            val fertilizer = plan.soilToFertilizer.getDestination(soil)
            val water = plan.fertilizerToWater.getDestination(fertilizer)
            val light = plan.waterToLight.getDestination(water)
            val temperature = plan.lightToTemperature.getDestination(light)
            val humidity = plan.temperatureToHumidity.getDestination(temperature)
            val location = plan.humidityToLocation.getDestination(humidity)
            location
        }

        return planted.min()
    }

    private fun BufferedReader.readUntilBlank(): MutableMap<Int, String> {
        var str: String? = null
        val m = mutableMapOf<Int, String>()
        str = this.readLine().trim()
        var i = 0
        while (str != null && str != "") {
            m[i] = str
            str = this.readLine()?.trim()
            i += 1
        }
        return m
    }
}

fun main() {
    //println(Day05a.execute("day05/sample01.txt"))
    println(Day05a.execute("day05/problem01.txt"))
}