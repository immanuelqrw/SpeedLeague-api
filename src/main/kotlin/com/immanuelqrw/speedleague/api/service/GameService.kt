package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.dto.input.Game as GameInput
import com.immanuelqrw.speedleague.api.dto.output.Game as GameOutput
import com.immanuelqrw.speedleague.api.entity.Game
import com.immanuelqrw.speedleague.api.service.seek.GameSeekService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GameService {

    @Autowired
    private lateinit var gameSeekService: GameSeekService

    fun create(gameInput: GameInput): GameOutput {
        return gameInput.run {
            val game = Game(
                name = name,
                shorthand = shorthand
            )
            val createdGame: Game = gameSeekService.create(game)

            createdGame.output
        }
    }

    fun findAll(search: String?): Iterable<GameOutput> {
        return gameSeekService.findAll(search = search).map { game -> game.output }
    }

}
