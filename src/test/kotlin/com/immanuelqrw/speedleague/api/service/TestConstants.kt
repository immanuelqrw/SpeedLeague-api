package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.entity.LeagueType
import com.immanuelqrw.speedleague.api.entity.Outcome
import com.immanuelqrw.speedleague.api.entity.Region
import java.time.LocalDateTime

object TestConstants {

    const val VALID_GAME_NAME: String = "Pokémon Snap"
    const val INVALID_GAME_NAME: String = "Pokémon Snap 2"

    const val VALID_SHORTHAND: String = "pkmnsnap"
    const val INVALID_SHORTHAND: String = "pkmnsnap2"

    const val VALID_SYSTEM_NAME: String = "Wii U"
    const val INVALID_SYSTEM_NAME: String = "Playstation 64"
    
    const val VALID_LEAGUE_NAME: String = "Neverending"
    const val INVALID_LEAGUE_NAME: String = "GDQ-Champions"

    val VALID_LEAGUE_TYPE: LeagueType = LeagueType.POOL
    val INVALID_LEAGUE_TYPE: LeagueType = LeagueType.HEAD_TO_HEAD

    const val VALID_SEASON: Int = 1
    const val INVALID_SEASON: Int = -1

    const val VALID_TIER_NAME: String = "Alpha"
    const val INVALID_TIER_NAME: String = "Magikarp"

    const val VALID_TIER_LEVEL: Int = 1
    const val INVALID_TIER_LEVEL: Int = -1

    const val VALID_RUNNER_LIMIT: Int = 2
    const val INVALID_RUNNER_LIMIT: Int = -2

    val VALID_REGION: Region = Region.NTSC_U
    val INVALID_REGION: Region = Region.PAL

    const val VALID_CATEGORY: String = "Any%"
    const val INVALID_CATEGORY: String = "Jynx%"

    const val VALID_IS_EMULATED: Boolean = false
    const val INVALID_IS_EMULATED: Boolean = true

    const val VALID_VERSION: String = "1.0.0"
    const val INVALID_VERSION: String = "5.0.0-Alpha"

    const val VALID_RUNNER_NAME: String = "Shoo"
    const val INVALID_RUNNER_NAME: String = "Arjay"

    const val VALID_RACE_NAME: String = "Neverending-S1-W6-Friday-1"
    const val INVALID_RACE_NAME: String = "Shoo-vs-Quo-GrandFinals"

    const val VALID_RACE_RUNNER_TIME: Long = 73_800L
    const val INVALID_RACE_RUNNER_TIME: Long = 82_800L

    val VALID_OUTCOME: Outcome = Outcome.PENDING_VERIFICATION
    val INVALID_OUTCOME: Outcome = Outcome.DID_NOT_FINISH

    val VALID_STARTED_ON: LocalDateTime = LocalDateTime.now()
    val INVALID_STARTED_ON: LocalDateTime = LocalDateTime.MAX

    val VALID_JOINED_ON: LocalDateTime = LocalDateTime.now()
    val INVALID_JOINED_ON: LocalDateTime = LocalDateTime.MAX

    val VALID_ENDED_ON: LocalDateTime? = null
    val INVALID_ENDED_ON: LocalDateTime? = LocalDateTime.now()

    val VALID_REGISTRATION_ENDED_ON: LocalDateTime? = null
    val INVALID_REGISTRATION_ENDED_ON: LocalDateTime? = LocalDateTime.now()

    const val VALID_DEFAULT_TIME: Long = 3600L
    const val INVALID_DEFAULT_TIME: Long = 0L

    const val VALID_DEFAULT_POINTS: Int = 10
    const val INVALID_DEFAULT_POINTS: Int = -1

    const val VALID_PROMOTIONS: Int = 1
    const val INVALID_PROMOTIONS: Int = -1

    const val VALID_RELEGATIONS: Int = 1
    const val INVALID_RELEGATIONS: Int = -1

    const val VALID_PLACEMENT: Int = 1
    const val INVALID_PLACEMENT: Int = -1

}
