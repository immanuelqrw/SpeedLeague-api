package com.immanuelqrw.speedleague.api.service


fun racePoints(place: Int?): Int {
    return when (place) {
        null -> 0
        1 -> 10
        2 -> 7
        3 -> 5
        4 -> 4
        5 -> 3
        6 -> 2
        else -> 1
    }
}
