package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.dto.input.Cart as CartInput
import com.immanuelqrw.speedleague.api.dto.output.Cart as CartOutput
import com.immanuelqrw.speedleague.api.entity.*
import com.immanuelqrw.speedleague.api.service.seek.CartSeekService
import com.immanuelqrw.speedleague.api.service.seek.GameSeekService
import com.immanuelqrw.speedleague.api.service.seek.SystemSeekService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CartService {

    @Autowired
    private lateinit var cartSeekService: CartSeekService

    @Autowired
    private lateinit var gameSeekService: GameSeekService

    @Autowired
    private lateinit var systemSeekService: SystemSeekService

    fun create(cartInput: CartInput): CartOutput {
        return cartInput.run {
            val game: Game = gameSeekService.findByName(gameName)
            val system: System = systemSeekService.findByName(systemName)

            val cart = Cart(
                game = game,
                system = system,
                region = region,
                version = version
            )
            val createdCart: Cart = cartSeekService.create(cart)

            createdCart.output
        }
    }

    fun findAll(
        search: String?
    ): Iterable<CartOutput> {
        return cartSeekService.findAllActive(search = search).map { cart -> cart.output }
    }

    fun findAll(
        gameName: String?,
        systemName: String?,
        isEmulated: Boolean?,
        region: Region?,
        version: String?
    ): Iterable<CartOutput> {
        return cartSeekService
            .findAll(gameName, systemName, isEmulated, region, version)
            .map { cart -> cart.output }
    }

}
