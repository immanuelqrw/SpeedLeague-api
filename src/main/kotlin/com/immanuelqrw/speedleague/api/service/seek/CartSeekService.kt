package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.Cart
import com.immanuelqrw.speedleague.api.entity.Region
import com.immanuelqrw.speedleague.api.repository.CartRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class CartSeekService : BaseUniqueService<Cart>(Cart::class.java) {

    @Autowired
    private lateinit var cartRepository: CartRepository

    fun findAllByGame(gameName: String): List<Cart> {
        return cartRepository.findAllByGameNameAndRemovedOnIsNull(gameName)
    }

    fun findAllBySystem(systemName: String): List<Cart> {
        return cartRepository.findAllBySystemNameAndRemovedOnIsNull(systemName)
    }

    fun findAllByRegion(region: Region): List<Cart> {
        return cartRepository.findAllByRegionAndRemovedOnIsNull(region)
    }

    fun findAllByGameAndSystem(gameName: String, systemName: String): List<Cart> {
        return cartRepository.findAllByGameNameAndSystemNameAndRemovedOnIsNull(gameName, systemName)
    }

    fun findAllByGameAndRegion(gameName: String, region: Region): List<Cart> {
        return cartRepository.findAllByGameNameAndRegionAndRemovedOnIsNull(gameName, region)
    }

    fun findAllBySystemAndRegion(systemName: String, region: Region): List<Cart> {
        return cartRepository.findAllBySystemNameAndRegionAndRemovedOnIsNull(systemName, region)
    }

    fun find(gameName: String, systemName: String, isEmulated: Boolean, region: Region, version: String): Cart {
        return cartRepository
            .findFirstByGameNameAndSystemNameAndSystemIsEmulatedAndRegionAndVersionAndRemovedOnIsNull(gameName, systemName, isEmulated, region, version)
            ?: throw EntityNotFoundException()
    }

    fun findAll(gameName: String?, systemName: String?, isEmulated: Boolean?, region: Region?, version: String?): List<Cart> {
        return findAllActive()
            .asSequence()
            .filter { cart ->  gameName?.let { cart.game.name == gameName } ?: true }
            .filter { cart ->  systemName?.let { cart.system.name == systemName } ?: true }
            .filter { cart ->  isEmulated?.let { cart.system.isEmulated == isEmulated } ?: true }
            .filter { cart ->  region?.let { cart.region == region } ?: true }
            .filter { cart ->  version?.let { cart.version == version } ?: true }
            .toList()
    }

}
