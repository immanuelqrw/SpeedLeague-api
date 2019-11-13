package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.dto.input.Race as RaceInput
import com.immanuelqrw.speedleague.api.dto.output.Race as RaceOutput
import com.immanuelqrw.speedleague.api.entity.Race
import com.immanuelqrw.speedleague.api.entity.RaceRunner
import com.immanuelqrw.speedleague.api.entity.Tier
import com.immanuelqrw.speedleague.api.service.seek.LeagueSeekService
import com.immanuelqrw.speedleague.api.service.seek.RaceRunnerSeekService
import com.immanuelqrw.speedleague.api.service.seek.RaceSeekService
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.invoking
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class RaceServiceTest {

    private val validName: String = "Neverending-S1-W6-Friday-1"
    private val invalidName: String = "Shoo-vs-Quo-GrandFinals"

    private val validLeagueName: String = "Neverending"
    private val invalidLeagueName: String = "GDQ-Champions"

    private val validSeason: Int = 1
    private val invalidSeason: Int = -1

    private val validTierLevel: Int = 1
    private val invalidTierLevel: Int = -1

    private val validTierName: String = "Alpha"
    private val invalidTierName: String = "Magikarp"

    private val validTier: Tier = Tier(
        name = validTierName,
        level = validTierLevel
    )

    private val invalidTier: Tier = Tier(
        name = invalidTierName,
        level = invalidTierLevel
    )

    private val validStartedOn: LocalDateTime = LocalDateTime.now()
    private val invalidStartedOn: LocalDateTime = LocalDateTime.MAX

    private val validRunnerName: String = "Shoo"
    private val invalidRunnerName: String = "Arjay"

    private val validSearch: String? = "name:Neverending-S1-W6-Friday-1"
    private val invalidSearch: String? = "name:Shoo-vs-Quo-GrandFinals"

    private val validRaceInput: RaceInput = RaceInput(
        leagueName = validLeagueName,
        season = validSeason,
        tierLevel = validTierLevel,
        raceName = validName,
        startedOn = validStartedOn
    )

    private val invalidRaceInput: RaceInput = RaceInput(
        leagueName = invalidLeagueName,
        season = invalidSeason,
        tierLevel = invalidTierLevel,
        raceName = invalidName,
        startedOn = invalidStartedOn
    )

    @Mock
    private lateinit var validLeague: League

    @Mock
    private lateinit var invalidLeague: League

    private lateinit var validRace: Race
    private lateinit var invalidRace: Race

    private lateinit var validRaces: List<Race>
    private lateinit var validRaceOutputs: List<RaceOutput>

    private val noRaces: List<Race> = emptyList()
    private val noRaceOutputs: List<RaceOutput> = emptyList()

    @Mock
    private lateinit var validRaceRunner: RaceRunner

    private lateinit var validRaceRunners: List<RaceRunner>
    private val noRaceRunners: List<RaceRunner> = emptyList()

    @Mock
    private lateinit var raceSeekService: RaceSeekService

    @Mock
    private lateinit var leagueSeekService: LeagueSeekService

    @Mock
    private lateinit var raceRunnerSeekService: RaceRunnerSeekService

    @InjectMocks
    private lateinit var raceService: RaceService

    @Mock
    private lateinit var leagueService: LeagueService

    @BeforeAll
    fun setUp() {
        whenever(validLeague.name).thenReturn(validLeagueName)
        whenever(validLeague.season).thenReturn(validSeason)
        whenever(validLeague.tier).thenReturn(validTier)

        validRace = Race(
            name = validName,
            league = validLeague,
            startedOn = validStartedOn
        )

        invalidRace = Race(
            name = invalidName,
            league = invalidLeague,
            startedOn = invalidStartedOn
        )

        validRaces = listOf(validRace)
        validRaceOutputs = validRaces.map { race -> race.output }

        validRaceRunners = listOf(validRaceRunner)

        whenever(validRaceRunner.race).thenReturn(validRace)

        whenever(raceSeekService.findAllActive(search = validSearch)).thenReturn(validRaces)
        whenever(raceRunnerSeekService.findAllByRunner(validRunnerName)).thenReturn(validRaceRunners)
        whenever(raceSeekService.create(validRace)).thenReturn(validRace)
        whenever(leagueSeekService.find(validLeagueName, validSeason, validTierLevel)).thenReturn(validLeague)

        doNothing().whenever(leagueService).validateLeagueChange(Mockito.any(LocalDateTime::class.java), Mockito.anyString())
        // ! Add failure case for validateLeagueChange

        whenever(invalidLeague.name).thenReturn(invalidLeagueName)
        whenever(invalidLeague.season).thenReturn(invalidSeason)
        whenever(invalidLeague.tier).thenReturn(invalidTier)

        whenever(raceSeekService.findAllActive(search = invalidSearch)).thenReturn(noRaces)
        whenever(leagueSeekService.find(invalidLeagueName, invalidSeason, invalidTierLevel)).thenReturn(invalidLeague)
        whenever(raceRunnerSeekService.findAllByRunner(invalidRunnerName)).thenReturn(noRaceRunners)
        doThrow(IllegalArgumentException::class).whenever(raceSeekService).create(invalidRace)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid raceInput - when create - then return valid raceOutput`() {
            val expectedRaceOutput: RaceOutput = validRace.output

            val actualRaceOutput: RaceOutput = raceService.create(validRaceInput)

            actualRaceOutput shouldEqual expectedRaceOutput
        }

        @Test
        fun `given valid search - when findAll - then return valid raceOutputs`() {
            val expectedRaceOutputs: Iterable<RaceOutput> = validRaceOutputs

            val actualRaceOutputs: Iterable<RaceOutput> = raceService.findAll(validSearch)

            actualRaceOutputs shouldEqual expectedRaceOutputs
        }

        @Test
        fun `given valid runner name - when findAllRaces - then return valid raceOutputs`() {
            val expectedRaceOutputs: Iterable<RaceOutput> = validRaceOutputs

            val actualRaceOutputs: Iterable<RaceOutput> = raceService.findAllRaces(validRunnerName)

            actualRaceOutputs shouldEqual expectedRaceOutputs
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid raceInput - when create - then throw IllegalArgumentException`() {
            invoking { raceService.create(invalidRaceInput) } shouldThrow IllegalArgumentException::class
        }

        @Test
        fun `given invalid search - when findAll - then return no raceOutputs`() {
            val expectedRaceOutputs: Iterable<RaceOutput> = noRaceOutputs

            val actualRaceOutputs: Iterable<RaceOutput> = raceService.findAll(invalidSearch)

            actualRaceOutputs shouldEqual expectedRaceOutputs
        }

        @Test
        fun `given invalid runner name - when findAllRaces - then return no raceOutputs`() {
            val expectedRaceOutputs: Iterable<RaceOutput> = noRaceOutputs

            val actualRaceOutputs: Iterable<RaceOutput> = raceService.findAllRaces(invalidRunnerName)

            actualRaceOutputs shouldEqual expectedRaceOutputs
        }

    }

}
