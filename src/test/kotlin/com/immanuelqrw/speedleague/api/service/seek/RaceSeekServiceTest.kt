package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.speedleague.api.entity.Race
import com.immanuelqrw.speedleague.api.repository.RaceRepository
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
import javax.persistence.EntityNotFoundException

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class RaceSeekServiceTest {

    private val validName: String = "Neverending-S1-W6-Friday-1"
    private val invalidName: String = "Shoo-vs-Quo-GrandFinals"

    @Mock
    private lateinit var validRace: Race

    @Mock
    private lateinit var raceRepository: RaceRepository

    @InjectMocks
    private lateinit var raceSeekService: RaceSeekService

    @BeforeAll
    fun setUp() {
        whenever(raceRepository.findByNameAndRemovedOnIsNull(validName)).thenReturn(validRace)

        whenever(raceRepository.findByNameAndRemovedOnIsNull(invalidName)).thenReturn(null)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid name - when findByName - then return valid race`() {
            val expectedRace: Race = validRace

            val actualRace = raceSeekService.findByName(validName)

            actualRace shouldEqual expectedRace
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid name - when findByName - then throw EntityNotFoundException`() {
            invoking { raceSeekService.findByName(invalidName) } shouldThrow EntityNotFoundException::class
        }

    }

}
