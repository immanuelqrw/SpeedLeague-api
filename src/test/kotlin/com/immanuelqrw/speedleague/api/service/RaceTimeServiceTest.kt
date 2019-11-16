package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.entity.*
import com.immanuelqrw.speedleague.api.exception.NotRegisteredException
import com.immanuelqrw.speedleague.api.service.seek.LeagueRunnerSeekService
import com.immanuelqrw.speedleague.api.dto.input.RaceTime as RaceTimeInput
import com.immanuelqrw.speedleague.api.dto.output.RaceTime as RaceTimeOutput
import com.immanuelqrw.speedleague.api.dto.update.RaceTime as RaceTimeRegister
import com.immanuelqrw.speedleague.api.service.seek.RaceRunnerSeekService
import com.immanuelqrw.speedleague.api.service.seek.RaceSeekService
import com.immanuelqrw.speedleague.api.service.seek.RunnerSeekService
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
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import javax.persistence.EntityNotFoundException

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class RaceTimeServiceTest {

    private val validRaceName: String = "Neverending-S1-W6-Friday-1"
    private val invalidRaceName: String = "Shoo-vs-Quo-GrandFinals"

    private val validRunnerName: String = "Shoo"
    private val invalidRunnerName: String = "Arjay"

    private val validTime: Long = 73_800L
    private val invalidTime: Long = 82_800L

    private val validOutcome: Outcome = Outcome.PENDING_VERIFICATION
    private val invalidOutcome: Outcome = Outcome.DID_NOT_FINISH

    private val validJoinedOn: LocalDateTime = LocalDateTime.now()
    private val invalidJoinedOn: LocalDateTime = LocalDateTime.MAX

    private val validRaceTimeInput: RaceTimeInput = RaceTimeInput(
        runnerName = validRunnerName,
        raceName = validRaceName,
        time = validTime,
        outcome = validOutcome,
        joinedOn = validJoinedOn
    )

    private val invalidRaceTimeInput: RaceTimeInput = RaceTimeInput(
        runnerName = invalidRunnerName,
        raceName = invalidRaceName,
        time = invalidTime,
        outcome = invalidOutcome,
        joinedOn = invalidJoinedOn
    )

    private val validRaceTimeRegister: RaceTimeRegister = RaceTimeRegister(
        runnerName = validRunnerName,
        raceName = validRaceName,
        time = validTime,
        outcome = validOutcome
    )

    private val invalidRaceTimeRegister: RaceTimeRegister = RaceTimeRegister(
        runnerName = invalidRunnerName,
        raceName = invalidRaceName,
        time = invalidTime,
        outcome = invalidOutcome
    )
    
    @Mock
    private lateinit var validRace: Race

    @Mock
    private lateinit var invalidRace: Race

    @Mock
    private lateinit var validRunner: Runner

    @Mock
    private lateinit var invalidRunner: Runner

    @Mock
    private lateinit var validLeague: League

    @Mock
    private lateinit var invalidLeague: League

    @Mock
    private lateinit var validLeagueRunner: LeagueRunner

    @Mock
    private lateinit var invalidLeagueRunner: LeagueRunner

    private lateinit var validLeagueRunners: List<LeagueRunner>
    private val noLeagueRunners: List<LeagueRunner> = emptyList()

    private lateinit var validRaceRunner: RaceRunner
    private lateinit var invalidRaceRunner: RaceRunner

    @Mock
    private lateinit var raceSeekService: RaceSeekService

    @Mock
    private lateinit var runnerSeekService: RunnerSeekService

    @Mock
    private lateinit var leagueRunnerSeekService: LeagueRunnerSeekService

    @Mock
    private lateinit var raceRunnerSeekService: RaceRunnerSeekService

    @InjectMocks
    private lateinit var raceTimeService: RaceTimeService

    @BeforeAll
    fun setUp() {
        whenever(validRace.league).thenReturn(validLeague)
        whenever(validRace.name).thenReturn(validRaceName)
        whenever(validRunner.name).thenReturn(validRunnerName)
        whenever(validLeagueRunner.league).thenReturn(validLeague)

        validLeagueRunners = listOf(validLeagueRunner)

        validRaceRunner = RaceRunner(
            race = validRace,
            runner = validRunner,
            time = validTime,
            outcome = validOutcome,
            joinedOn = validJoinedOn
        )

        invalidRaceRunner = RaceRunner(
            race = invalidRace,
            runner = invalidRunner,
            time = invalidTime,
            outcome = invalidOutcome,
            joinedOn = invalidJoinedOn
        )

        whenever(invalidRace.league).thenReturn(invalidLeague)
        whenever(invalidRace.name).thenReturn(invalidRaceName)
        whenever(invalidRunner.name).thenReturn(invalidRunnerName)
        
        whenever(raceSeekService.findByName(validRaceName)).thenReturn(validRace)
        whenever(runnerSeekService.findByName(validRunnerName)).thenReturn(validRunner)
        whenever(leagueRunnerSeekService.findAllByRunner(validRunnerName)).thenReturn(validLeagueRunners)
        whenever(raceRunnerSeekService.create(validRaceRunner)).thenReturn(validRaceRunner)
        whenever(raceRunnerSeekService.registerRaceTime(validRaceTimeRegister)).thenReturn(validRaceRunner)
        
        whenever(raceSeekService.findByName(invalidRaceName)).thenReturn(invalidRace)
        whenever(runnerSeekService.findByName(invalidRunnerName)).thenReturn(invalidRunner)
        doThrow(IllegalArgumentException::class).whenever(raceRunnerSeekService).create(invalidRaceRunner)
        doThrow(EntityNotFoundException::class).whenever(raceRunnerSeekService).registerRaceTime(invalidRaceTimeRegister)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid raceTimeInput - when register - then return valid raceTimeOutput`() {
            val expectedRaceTimeOutput: RaceTimeOutput = validRaceRunner.output

            val actualRaceTimeOutput: RaceTimeOutput = raceTimeService.register(validRaceTimeInput)

            actualRaceTimeOutput shouldEqual expectedRaceTimeOutput
        }

        @Test
        fun `given valid raceTimeRegister - when registerTime - then return valid raceTimeOutput`() {
            val expectedRaceTimeOutput: RaceTimeOutput = validRaceRunner.output

            val actualRaceTimeOutput: RaceTimeOutput = raceTimeService.registerTime(validRaceTimeRegister)

            actualRaceTimeOutput shouldEqual expectedRaceTimeOutput
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid invalidRaceTimeInput - when register - then throw IllegalArgumentException`() {
            val invalidLeagueRunners: List<LeagueRunner> = listOf(invalidLeagueRunner)
            whenever(invalidLeagueRunner.league).thenReturn(invalidLeague)
            whenever(leagueRunnerSeekService.findAllByRunner(invalidRunnerName)).thenReturn(invalidLeagueRunners)

            invoking { raceTimeService.register(invalidRaceTimeInput) } shouldThrow IllegalArgumentException::class
        }

        @Test
        fun `given unregistered runner - when register - then throw NotRegisteredException`() {
            whenever(leagueRunnerSeekService.findAllByRunner(invalidRunnerName)).thenReturn(noLeagueRunners)

            invoking { raceTimeService.register(invalidRaceTimeInput) } shouldThrow NotRegisteredException::class
        }

        @Test
        fun `given invalid raceTimeRegister - when registerTime - then throw EntityNotFoundException`() {
            invoking { raceTimeService.registerTime(invalidRaceTimeRegister) } shouldThrow EntityNotFoundException::class
        }

    }

}
