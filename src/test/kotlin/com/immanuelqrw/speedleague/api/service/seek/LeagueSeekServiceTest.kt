package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.exception.LeagueHasEndedException
import com.immanuelqrw.speedleague.api.repository.LeagueRepository
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
internal class LeagueSeekServiceTest {

    private val validName: String = "Pokémon Snap"
    private val invalidName: String = "Pokémon Snap 2"

    private val validSeason: Int = 1
    private val invalidSeason: Int = -1

    private val validTierLevel: Int = 1
    private val invalidTierLevel: Int = -1

    private val noLeagues: List<League> = emptyList()

    @Mock
    private lateinit var validLeague: League

    @Mock
    private lateinit var validLeagues: List<League>

    @Mock
    private lateinit var leagueRepository: LeagueRepository

    @InjectMocks
    private lateinit var leagueSeekService: LeagueSeekService

    @BeforeAll
    fun setUp() {
        whenever(leagueRepository.findByNameAndSeasonAndTierLevelAndRemovedOnIsNull(validName, validSeason, validTierLevel)).thenReturn(validLeague)
        whenever(leagueRepository.findAllByNameAndSeasonAndRemovedOnIsNullOrderByTierLevelAsc(validName, validSeason)).thenReturn(validLeagues)
        whenever(leagueRepository.findFirstByNameAndSeasonAndRemovedOnIsNullOrderByTierLevelDesc(validName, validSeason)).thenReturn(validLeague)

        whenever(leagueRepository.findByNameAndSeasonAndTierLevelAndRemovedOnIsNull(invalidName, invalidSeason, invalidTierLevel)).thenReturn(null)
        whenever(leagueRepository.findAllByNameAndSeasonAndRemovedOnIsNullOrderByTierLevelAsc(invalidName, invalidSeason)).thenReturn(noLeagues)
        whenever(leagueRepository.findFirstByNameAndSeasonAndRemovedOnIsNullOrderByTierLevelDesc(invalidName, invalidSeason)).thenReturn(null)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid parameters - when find - then return valid league`() {
            val expectedLeague: League = validLeague

            val actualLeague: League = leagueSeekService.find(validName, validSeason, validTierLevel)

            actualLeague shouldEqual expectedLeague
        }

        @Test
        fun `given valid parameters - when findAllTiers - then return valid leagues`() {
            val expectedLeagues: List<League> = validLeagues

            val actualLeagues: List<League> = leagueSeekService.findAllTiers(validName, validSeason)

            actualLeagues shouldEqual expectedLeagues
        }

        @Test
        fun `given valid parameters - when findBottomLeague - then return valid league`() {
            val expectedLeague: League = validLeague

            val actualLeague: League = leagueSeekService.findBottomLeague(validName, validSeason)

            actualLeague shouldEqual expectedLeague
        }

        @Test
        fun findAllActive() {
            TODO()
        }

        @Test
        fun `given null endedOn - when validateLeagueChange - then do nothing`() {
            val endedOn: LocalDateTime? = null

            leagueSeekService.validateLeagueChange(endedOn)
        }

        @Test
        fun endLeague() {
            TODO()
        }

        @Test
        fun create() {
            TODO()
        }

        @Test
        fun updateDivisionShifts() {
            TODO()
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid parameters - when find - then throw EntityNotFoundException`() {
            invoking { leagueSeekService.find(invalidName, invalidSeason, invalidTierLevel) } shouldThrow EntityNotFoundException::class
        }

        @Test
        fun `given invalid parameters - when findAllTiers - then return no leagues`() {
            val expectedLeagues: List<League> = noLeagues

            val actualLeagues: List<League> = leagueSeekService.findAllTiers(invalidName, invalidSeason)

            actualLeagues shouldEqual expectedLeagues
        }

        @Test
        fun `given invalid parameters - when findBottomLeague - then throw EntityNotFoundException`() {
            invoking { leagueSeekService.findBottomLeague(invalidName, invalidSeason) } shouldThrow EntityNotFoundException::class
        }

        @Test
        fun `given non-null endedOn - when validateLeagueChange - then throw LeagueHasEndedException`() {
            val endedOn: LocalDateTime? = LocalDateTime.now()

            invoking { leagueSeekService.validateLeagueChange(endedOn) } shouldThrow LeagueHasEndedException::class
        }

    }

}
