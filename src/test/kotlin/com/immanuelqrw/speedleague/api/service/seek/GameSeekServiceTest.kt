package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.speedleague.api.entity.Game
import com.immanuelqrw.speedleague.api.repository.GameRepository
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
internal class GameSeekServiceTest {

    private val validName: String = "Pokémon Snap"
    private val invalidName: String = "Pokémon Snap 2"

    private val validShorthand: String = "pkmnsnap"
    private val invalidShorthand: String = "pkmnsnap2"

    @Mock
    private lateinit var validGame: Game

    @Mock
    private lateinit var gameRepository: GameRepository

    @InjectMocks
    private lateinit var gameSeekService: GameSeekService

    @BeforeAll
    fun setUp() {
        whenever(gameRepository.findByNameAndRemovedOnIsNull(validName)).thenReturn(validGame)
        whenever(gameRepository.findByShorthandAndRemovedOnIsNull(validShorthand)).thenReturn(validGame)

        whenever(gameRepository.findByNameAndRemovedOnIsNull(invalidName)).thenReturn(null)
        whenever(gameRepository.findByShorthandAndRemovedOnIsNull(invalidShorthand)).thenReturn(null)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid name - when findByName - then return valid game`() {
            val expectedGame: Game = validGame

            val actualGame: Game = gameSeekService.findByName(validName)

            actualGame shouldEqual expectedGame
        }

        @Test
        fun `given valid shorthand - when findByShorthand - then return valid game`() {
            val expectedGame: Game = validGame

            val actualGame: Game = gameSeekService.findByShorthand(validShorthand)

            actualGame shouldEqual expectedGame
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid name - when findByName - then throw EntityNotFoundException`() {
            invoking { gameSeekService.findByName(invalidName) } shouldThrow EntityNotFoundException::class
        }

        @Test
        fun `given invalid shorthand - when findByShorthand - then throw EntityNotFoundException`() {
            invoking { gameSeekService.findByShorthand(invalidShorthand) } shouldThrow EntityNotFoundException::class
        }

    }

}
