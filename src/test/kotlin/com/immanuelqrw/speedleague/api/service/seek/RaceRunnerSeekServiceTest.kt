package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.speedleague.api.dto.update.RaceTime as RaceTimeRegister
import com.immanuelqrw.speedleague.api.entity.Outcome
import com.immanuelqrw.speedleague.api.entity.RaceRunner
import com.immanuelqrw.speedleague.api.repository.RaceRunnerRepository
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
import javax.persistence.EntityNotFoundException

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class RaceRunnerSeekServiceTest {

    private val validRaceName: String = "Neverending-S1-W6-Friday-1"
    private val invalidRaceName: String = "Shoo-vs-Quo-GrandFinals"

    private val validRunnerName: String = "Shoo"
    private val invalidRunnerName: String = "Arjay"

    private val validStartedOn: LocalDateTime = LocalDateTime.now()
    private val invalidStartedOn: LocalDateTime = LocalDateTime.MIN

    private val validOutcome: Outcome = Outcome.DID_NOT_FINISH

    private val noRaceRunners: List<RaceRunner> = emptyList()

    private val validRaceTimeRegister: RaceTimeRegister = RaceTimeRegister(
        runnerName = validRunnerName,
        raceName = validRaceName,
        time = 0,
        outcome = validOutcome
    )
    private val invalidRaceTimeRegister: RaceTimeRegister = RaceTimeRegister(
        runnerName = invalidRunnerName,
        raceName = invalidRaceName,
        time = 0,
        outcome = validOutcome
    )

    @Mock
    private lateinit var validRaceRunner: RaceRunner

    @Mock
    private lateinit var validRaceRunners: List<RaceRunner>

    @Mock
    private lateinit var raceRunnerRepository: RaceRunnerRepository

    @InjectMocks
    private lateinit var raceRunnerSeekService: RaceRunnerSeekService

    @BeforeAll
    fun setUp() {
        whenever(raceRunnerRepository.save(Mockito.any(RaceRunner::class.java))).thenReturn(validRaceRunner)

        whenever(raceRunnerRepository.findByRaceNameAndRunnerNameAndRemovedOnIsNull(validRaceName, validRunnerName)).thenReturn(validRaceRunner)
        whenever(raceRunnerRepository.findAllByRaceNameAndRemovedOnIsNull(validRaceName)).thenReturn(validRaceRunners)
        whenever(raceRunnerRepository.findAllByRunnerNameAndRemovedOnIsNull(validRunnerName)).thenReturn(validRaceRunners)
        whenever(raceRunnerRepository.findAllByRaceStartedOnBeforeAndOutcomeAndRemovedOnIsNull(validStartedOn, validOutcome)).thenReturn(validRaceRunners)
        whenever(raceRunnerRepository.findAllByRaceNameAndOutcomeAndRemovedOnIsNull(validRaceName, validOutcome)).thenReturn(validRaceRunners)

        whenever(raceRunnerRepository.findByRaceNameAndRunnerNameAndRemovedOnIsNull(invalidRaceName, invalidRunnerName)).thenReturn(null)
        whenever(raceRunnerRepository.findAllByRaceNameAndRemovedOnIsNull(invalidRaceName)).thenReturn(noRaceRunners)
        whenever(raceRunnerRepository.findAllByRunnerNameAndRemovedOnIsNull(invalidRunnerName)).thenReturn(noRaceRunners)
        whenever(raceRunnerRepository.findAllByRaceStartedOnBeforeAndOutcomeAndRemovedOnIsNull(invalidStartedOn, validOutcome)).thenReturn(noRaceRunners)
        whenever(raceRunnerRepository.findAllByRaceNameAndOutcomeAndRemovedOnIsNull(invalidRaceName, validOutcome)).thenReturn(noRaceRunners)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid race and runner names - when findByRaceAndRunner - then return valid raceRunner`() {
            val expectedRaceRunner: RaceRunner = validRaceRunner

            val actualRaceRunner = raceRunnerSeekService.findByRaceAndRunner(validRaceName, validRunnerName)

            actualRaceRunner shouldEqual expectedRaceRunner
        }

        @Test
        fun `given valid race name - when findAllByRace- then return valid raceRunners`() {
            val expectedRaceRunners: List<RaceRunner> = validRaceRunners

            val actualRaceRunners = raceRunnerSeekService.findAllByRace(validRaceName)

            actualRaceRunners shouldEqual expectedRaceRunners
        }

        @Test
        fun `given valid runner name - when findAllByRunner - then return valid raceRunners`() {
            val expectedRaceRunners: List<RaceRunner> = validRaceRunners

            val actualRaceRunners = raceRunnerSeekService.findAllByRunner(validRunnerName)

            actualRaceRunners shouldEqual expectedRaceRunners
        }

        @Test
        fun `given valid parameters - when findAllByStartedOnAndOutcome - then return valid raceRunners`() {
            val expectedRaceRunners: List<RaceRunner> = validRaceRunners

            val actualRaceRunners = raceRunnerSeekService.findAllByStartedOnAndOutcome(validStartedOn, validOutcome)

            actualRaceRunners shouldEqual expectedRaceRunners
        }

        @Test
        fun `given valid parameters - when findAllByRaceAndOutcome - then return valid raceRunners`() {
            val expectedRaceRunners: List<RaceRunner> = validRaceRunners

            val actualRaceRunners = raceRunnerSeekService.findAllByRaceAndOutcome(validRaceName, validOutcome)

            actualRaceRunners shouldEqual expectedRaceRunners
        }

        @Test
        fun `given valid raceTimeRegister - when registerRaceTime - then return valid raceRunner`() {
            val expectedRaceRunner: RaceRunner = validRaceRunner

            val actualRaceRunner = raceRunnerSeekService.registerRaceTime(validRaceTimeRegister)

            actualRaceRunner shouldEqual expectedRaceRunner
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid parameters - when findByRaceAndRunner - then throw EntityNotFoundException`() {
            invoking { raceRunnerSeekService.findByRaceAndRunner(invalidRaceName, invalidRunnerName) } shouldThrow EntityNotFoundException::class
        }

        @Test
        fun `given invalid parameters - when findAllByRace- then return no raceRunners`() {
            val expectedRaceRunners: List<RaceRunner> = noRaceRunners

            val actualRaceRunners = raceRunnerSeekService.findAllByRace(invalidRaceName)

            actualRaceRunners shouldEqual expectedRaceRunners
        }

        @Test
        fun `given invalid parameters - when findAllByRunner - then return no raceRunners`() {
            val expectedRaceRunners: List<RaceRunner> = noRaceRunners

            val actualRaceRunners = raceRunnerSeekService.findAllByRunner(invalidRunnerName)

            actualRaceRunners shouldEqual expectedRaceRunners
        }

        @Test
        fun `given invalid parameters - when findAllByStartedOnAndOutcome - then return no raceRunners`() {
            val expectedRaceRunners: List<RaceRunner> = noRaceRunners

            val actualRaceRunners = raceRunnerSeekService.findAllByStartedOnAndOutcome(invalidStartedOn, validOutcome)

            actualRaceRunners shouldEqual expectedRaceRunners
        }

        @Test
        fun `given invalid parameters - when findAllByRaceAndOutcome - then return no raceRunners`() {
            val expectedRaceRunners: List<RaceRunner> = noRaceRunners

            val actualRaceRunners = raceRunnerSeekService.findAllByRaceAndOutcome(invalidRunnerName, validOutcome)

            actualRaceRunners shouldEqual expectedRaceRunners
        }

        @Test
        fun `given invalid raceTimeRegister - when registerRaceTime - then throw EntityNotFoundException`() {
            invoking { raceRunnerSeekService.registerRaceTime(invalidRaceTimeRegister) } shouldThrow EntityNotFoundException::class
        }

    }

}
