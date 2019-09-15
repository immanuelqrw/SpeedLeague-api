package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.input.Cart as CartInput
import com.immanuelqrw.speedleague.api.dto.output.Cart as CartOutput
import com.immanuelqrw.speedleague.api.entity.Cart
import com.immanuelqrw.speedleague.api.entity.DistinctSystem
import com.immanuelqrw.speedleague.api.entity.Game
import com.immanuelqrw.speedleague.api.service.seek.CartService
import com.immanuelqrw.speedleague.api.service.seek.DistinctSystemService
import com.immanuelqrw.speedleague.api.service.seek.GameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cart")
class CartController {

    @Autowired
    private lateinit var cartService: CartService

    @Autowired
    private lateinit var gameService: GameService

    @Autowired
    private lateinit var distinctSystemService: DistinctSystemService

    private fun convertToOutput(cart: Cart): CartOutput {
        return cart.run {
            CartOutput(
                gameName = cart.game.name,
                systemName = cart.distinctSystem.system.name,
                isEmulated = cart.distinctSystem.system.isEmulated,
                region = cart.distinctSystem.region,
                versionName = cart.distinctSystem.version?.name
            )
        }
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody cartInput: CartInput): CartOutput {
        return cartInput.run {
            val game: Game = gameService.findByName(gameName)
            val distinctSystem: DistinctSystem = distinctSystemService.findBySystemAndRegionAndVersion(systemName, region, versionName)

            val cart = Cart(
                game = game,
                distinctSystem = distinctSystem
            )
            val createdCart: Cart = cartService.create(cart)

            convertToOutput(createdCart)
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<CartOutput> {
        return cartService.findAll(search = search).map { cart -> convertToOutput(cart) }
    }

}
