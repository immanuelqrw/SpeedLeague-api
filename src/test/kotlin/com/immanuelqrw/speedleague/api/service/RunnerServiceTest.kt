package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.entity.*
import com.immanuelqrw.speedleague.api.exception.LeagueIsFullException
import com.immanuelqrw.speedleague.api.service.seek.LeagueRunnerSeekService
import com.immanuelqrw.speedleague.api.dto.input.LeagueRunner as LeagueRunnerInput
import com.immanuelqrw.speedleague.api.dto.output.LeagueRunner as LeagueRunnerOutput
import com.immanuelqrw.speedleague.api.dto.input.Runner as RunnerInput
import com.immanuelqrw.speedleague.api.dto.output.Runner as RunnerOutput
import com.immanuelqrw.speedleague.api.service.seek.LeagueSeekService
import com.immanuelqrw.speedleague.api.service.seek.RaceRunnerSeekService
import com.immanuelqrw.speedleague.api.service.seek.RunnerSeekService
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
internal class RunnerServiceTest {

    private val validName: String = "Shoo"
    private val invalidName: String = "Arjay"

    private val validJoinedOn: LocalDateTime = LocalDateTime.now()
    private val invalidJoinedOn: LocalDateTime = LocalDateTime.MAX

    private val validRaceName: String = "Neverending-S1-W6-Friday-1"
    private val invalidRaceName: String = "Shoo-vs-Quo-GrandFinals"

    private val validLeagueName: String = "Neverending"
    private val invalidLeagueName: String = "GDQ-Champions"

    private val validSeason: Int = 1
    private val invalidSeason: Int = -1

    private val validTierLevel: Int = 1
    private val invalidTierLevel: Int = -1

    private val validTierName: String = "Alpha"
    private val invalidTierName: String = "Magikarp"
    
    private val validRunnerLimit: Int = 2
    private val invalidRunnerLimit: Int = -2

    private val validTier: Tier = Tier(
        name = validTierName,
        level = validTierLevel
    )

    private val invalidTier: Tier = Tier(
        name = invalidTierName,
        level = invalidTierLevel
    )

    private val validSearch: String? = "name:Shoo"
    private val invalidSearch: String? = "name:Arjay"

    private val validRunnerInput: RunnerInput = RunnerInput(
        name = validName,
        joinedOn = validJoinedOn
    )

    private val invalidRunnerInput: RunnerInput = RunnerInput(
        name = invalidName,
        joinedOn = invalidJoinedOn
    )
    
    private val validLeagueRunnerInput: LeagueRunnerInput = LeagueRunnerInput(
        leagueName = validLeagueName,
        season = validSeason,
        runnerName = validName,
        joinedOn = validJoinedOn
    )

    private val invalidLeagueRunnerInput: LeagueRunnerInput = LeagueRunnerInput(
        leagueName = invalidLeagueName,
        season = invalidSeason,
        runnerName = invalidName,
        joinedOn = invalidJoinedOn
    )

    @Mock
    private lateinit var validLeague: League

    @Mock
    private lateinit var invalidLeague: League

    private lateinit var validRunner: Runner
    private lateinit var invalidRunner: Runner

    private lateinit var validRunners: List<Runner>
    private lateinit var validRunnerOutputs: List<RunnerOutput>

    private val noRunners: List<Runner> = emptyList()
    private val noRunnerOutputs: List<RunnerOutput> = emptyList()

    @Mock
    private lateinit var validRaceRunner: RaceRunner

    private lateinit var validRaceRunners: List<RaceRunner>
    private val noRaceRunners: List<RaceRunner> = emptyList()

    private lateinit var validLeagueRunner: LeagueRunner
    private lateinit var invalidLeagueRunner: LeagueRunner
    
    private lateinit var validLeagueRunners: List<LeagueRunner>
    private val noLeagueRunners: List<LeagueRunner> = emptyList()

    @Mock
    private lateinit var runnerSeekService: RunnerSeekService

    @Mock
    private lateinit var leagueSeekService: LeagueSeekService

    @Mock
    private lateinit var raceRunnerSeekService: RaceRunnerSeekService

    @Mock
    private lateinit var leagueRunnerSeekService: LeagueRunnerSeekService

    @InjectMocks
    private lateinit var runnerService: RunnerService

    @Mock
    private lateinit var leagueService: LeagueService

    @BeforeAll
    fun setUp() {
        whenever(validLeague.name).thenReturn(validLeagueName)
        whenever(validLeague.season).thenReturn(validSeason)
        whenever(validLeague.tier).thenReturn(validTier)
        whenever(validLeague.runnerLimit).thenReturn(validRunnerLimit)

        validRunner = Runner(
            name = validName,
            joinedOn = validJoinedOn
        )

        invalidRunner = Runner(
            name = invalidName,
            joinedOn = invalidJoinedOn
        )

        validRunners = listOf(validRunner)
        validRunnerOutputs = validRunners.map { race -> race.output }

        validRaceRunners = listOf(validRaceRunner)

        validLeagueRunner = LeagueRunner(
            league = validLeague,
            runner = validRunner,
            joinedOn = validJoinedOn
        )

        invalidLeagueRunner = LeagueRunner(
            league = invalidLeague,
            runner = invalidRunner,
            joinedOn = invalidJoinedOn
        )

        validLeagueRunners = listOf(validLeagueRunner)

        whenever(validRaceRunner.runner).thenReturn(validRunner)

        whenever(runnerSeekService.findAllActive(search = validSearch)).thenReturn(validRunners)
        whenever(runnerSeekService.findByName(validName)).thenReturn(validRunner)
        whenever(raceRunnerSeekService.findAllByRace(validRaceName)).thenReturn(validRaceRunners)
        whenever(runnerSeekService.create(validRunner)).thenReturn(validRunner)
        whenever(leagueSeekService.find(validLeagueName, validSeason, validTierLevel)).thenReturn(validLeague)
        whenever(leagueSeekService.findBottomLeague(validLeagueName, validSeason)).thenReturn(validLeague)
        whenever(leagueRunnerSeekService.findAllByLeague(validLeagueName, validSeason, validTierLevel)).thenReturn(validLeagueRunners)
        whenever(leagueRunnerSeekService.create(validLeagueRunner)).thenReturn(validLeagueRunner)

        doNothing().whenever(leagueService).validateLeagueChange(Mockito.any(LocalDateTime::class.java), Mockito.anyString())
        // ! Add failure case for validateLeagueChange

        whenever(invalidLeague.name).thenReturn(invalidLeagueName)
        whenever(invalidLeague.season).thenReturn(invalidSeason)
        whenever(invalidLeague.tier).thenReturn(invalidTier)

        whenever(runnerSeekService.findAllActive(search = invalidSearch)).thenReturn(noRunners)
        whenever(runnerSeekService.findByName(invalidName)).thenReturn(invalidRunner)
        whenever(leagueSeekService.find(invalidLeagueName, invalidSeason, invalidTierLevel)).thenReturn(invalidLeague)
        whenever(leagueSeekService.findBottomLeague(invalidLeagueName, invalidSeason)).thenReturn(invalidLeague)
        whenever(raceRunnerSeekService.findAllByRace(invalidRaceName)).thenReturn(noRaceRunners)
        whenever(leagueRunnerSeekService.findAllByLeague(invalidLeagueName, invalidSeason, invalidTierLevel)).thenReturn(noLeagueRunners)
        doThrow(IllegalArgumentException::class).whenever(leagueRunnerSeekService).create(invalidLeagueRunner)
        doThrow(IllegalArgumentException::class).whenever(runnerSeekService).create(invalidRunner)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid runnerInput - when create - then return valid runnerOutput`() {
            val expectedRunnerOutput: RunnerOutput = validRunner.output

            val actualRunnerOutput: RunnerOutput = runnerService.create(validRunnerInput)

            actualRunnerOutput shouldEqual expectedRunnerOutput
        }

        @Test
        fun `given valid leagueRunnerInput - when register - then return valid leagueRunnerOutput`() {
            val expectedLeagueRunnerOutput: LeagueRunnerOutput = validLeagueRunner.output

            val actualLeagueRunnerOutput: LeagueRunnerOutput = runnerService.register(validLeagueRunnerInput)

            actualLeagueRunnerOutput shouldEqual expectedLeagueRunnerOutput
        }

        @Test
        fun `given valid search - when findAll - then return valid runnerOutputs`() {
            val expectedRunnerOutputs: Iterable<RunnerOutput> = validRunnerOutputs

            val actualRunnerOutputs: Iterable<RunnerOutput> = runnerService.findAll(validSearch)

            actualRunnerOutputs shouldEqual expectedRunnerOutputs
        }

        @Test
        fun `given valid race name - when findAllRunners - then return valid runnerOutputs`() {
            val expectedRunnerOutputs: Iterable<RunnerOutput> = validRunnerOutputs

            val actualRunnerOutputs: Iterable<RunnerOutput> = runnerService.findAllRunners(validRaceName)

            actualRunnerOutputs shouldEqual expectedRunnerOutputs
        }

        @Test
        fun `given valid parameters - when findAllRunners - then return valid runnerOutputs`() {
            val expectedRunnerOutputs: Iterable<RunnerOutput> = validRunnerOutputs

            val actualRunnerOutputs: Iterable<RunnerOutput> = runnerService.findAllRunners(validLeagueName, validSeason, validTierLevel)

            actualRunnerOutputs shouldEqual expectedRunnerOutputs
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid runnerInput - when create - then throw IllegalArgumentException`() {
            invoking { runnerService.create(invalidRunnerInput) } shouldThrow IllegalArgumentException::class
        }

        @Test
        fun `given invalid runnerLimit - when register - then throw LeagueIsFullException`() {
            whenever(invalidLeague.runnerLimit).thenReturn(invalidRunnerLimit)
            invoking { runnerService.register(invalidLeagueRunnerInput) } shouldThrow LeagueIsFullException::class
        }

        @Test
        fun `given invalid leagueRunnerInput - when register - then throw IllegalArgumentException`() {
            whenever(invalidLeague.runnerLimit).thenReturn(validRunnerLimit)
            invoking { runnerService.register(invalidLeagueRunnerInput) } shouldThrow IllegalArgumentException::class
        }

        @Test
        fun `given invalid search - when findAll - then return no runnerOutputs`() {
            val expectedRunnerOutputs: Iterable<RunnerOutput> = noRunnerOutputs

            val actualRunnerOutputs: Iterable<RunnerOutput> = runnerService.findAll(invalidSearch)

            actualRunnerOutputs shouldEqual expectedRunnerOutputs
        }

        @Test
        fun `given invalid race name - when findAllRunners - then return no runnerOutputs`() {
            val expectedRunnerOutputs: Iterable<RunnerOutput> = noRunnerOutputs

            val actualRunnerOutputs: Iterable<RunnerOutput> = runnerService.findAllRunners(invalidRaceName)

            actualRunnerOutputs shouldEqual expectedRunnerOutputs
        }

        @Test
        fun `given invalid parameters - when findAllRunners - then return no runnerOutputs`() {
            val expectedRunnerOutputs: Iterable<RunnerOutput> = noRunnerOutputs

            val actualRunnerOutputs: Iterable<RunnerOutput> = runnerService.findAllRunners(invalidLeagueName, invalidSeason, invalidTierLevel)

            actualRunnerOutputs shouldEqual expectedRunnerOutputs
        }

    }

}
