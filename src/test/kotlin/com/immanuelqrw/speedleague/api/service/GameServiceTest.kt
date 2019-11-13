package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.dto.input.Game as GameInput
import com.immanuelqrw.speedleague.api.dto.output.Game as GameOutput
import com.immanuelqrw.speedleague.api.entity.Game
import com.immanuelqrw.speedleague.api.service.seek.GameSeekService
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
import javax.persistence.EntityNotFoundException

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class GameServiceTest {

    private val validName: String = "Pokémon Snap"
    private val invalidName: String = "Pokémon Snap 2"

    private val validShorthand: String = "pkmnsnap"
    private val invalidShorthand: String = "pkmnsnap2"

    private val validSearch: String? = "name:Pokémon Snap"
    private val invalidSearch: String? = "name:Pokémon Snap 2"

    private val validGameInput: GameInput = GameInput(
        name = validName,
        shorthand = validShorthand
    )

    private val invalidGameInput: GameInput = GameInput(
        name = invalidName,
        shorthand = invalidShorthand
    )

    private val validGame: Game = Game(
        name = validName,
        shorthand = validShorthand
    )

    private val invalidGame: Game = Game(
        name = invalidName,
        shorthand = invalidShorthand
    )

    private val validGames: List<Game> = listOf(validGame)
    private val validGameOutputs: List<GameOutput> = validGames.map { game -> game.output }

    private val noGames: List<Game> = emptyList()
    private val noGameOutputs: List<GameOutput> = emptyList()

    @Mock
    private lateinit var gameSeekService: GameSeekService

    @InjectMocks
    private lateinit var gameService: GameService

    @BeforeAll
    fun setUp() {
        whenever(gameSeekService.findAllActive(search = validSearch)).thenReturn(validGames)
        whenever(gameSeekService.findByShorthand(validShorthand)).thenReturn(validGame)
        whenever(gameSeekService.create(validGame)).thenReturn(validGame)

        whenever(gameSeekService.findAllActive(search = invalidSearch)).thenReturn(noGames)
        doThrow(EntityNotFoundException::class).whenever(gameSeekService).findByShorthand(invalidShorthand)
        doThrow(IllegalArgumentException::class).whenever(gameSeekService).create(invalidGame)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid gameInput - when create - then return valid gameOutput`() {
            val expectedGameOutput: GameOutput = validGame.output

            val actualGameOutput: GameOutput = gameService.create(validGameInput)

            actualGameOutput shouldEqual expectedGameOutput
        }

        @Test
        fun `given valid search - when findAll - then return valid gameOutputs`() {
            val expectedGameOutputs: Iterable<GameOutput> = validGameOutputs

            val actualGameOutputs: Iterable<GameOutput> = gameService.findAll(validSearch)

            actualGameOutputs shouldEqual expectedGameOutputs
        }

        @Test
        fun `given valid shorthand - when findByShorthand - then return valid gameOutput`() {
            val expectedGameOutput: GameOutput = validGame.output

            val actualGameOutput: GameOutput = gameService.findByShorthand(validShorthand)

            actualGameOutput shouldEqual expectedGameOutput
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid gameInput - when create - then throw IllegalArgumentException`() {
            invoking { gameService.create(invalidGameInput) } shouldThrow IllegalArgumentException::class
        }

        @Test
        fun `given invalid search - when findAll - then return no gameOutputs`() {
            val expectedGameOutputs: Iterable<GameOutput> = noGameOutputs

            val actualGameOutputs: Iterable<GameOutput> = gameService.findAll(invalidSearch)

            actualGameOutputs shouldEqual expectedGameOutputs
        }

        @Test
        fun `given invalid shorthand - when findByShorthand - then throw EntityNotFoundException`() {
            invoking { gameService.findByShorthand(invalidShorthand) } shouldThrow EntityNotFoundException::class
        }

    }

}
