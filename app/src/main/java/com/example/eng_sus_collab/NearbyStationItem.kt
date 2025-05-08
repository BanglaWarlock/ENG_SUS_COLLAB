package com.example.eng_sus_collab

import kotlin.random.Random
import kotlin.random.nextInt

data class NearbyStationItem(var station_name: String,
                             var station_distance: Double,
                             var battery_percents: List<Int> = List(64) {Random.nextInt(0, 101)}

)

// until is exclusive