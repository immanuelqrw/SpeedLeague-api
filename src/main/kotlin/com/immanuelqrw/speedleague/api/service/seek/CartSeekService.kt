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
        return cartRepository.findAllByGameName(gameName)
    }

    fun findAllBySystem(systemName: String): List<Cart> {
        return cartRepository.findAllBySystemName(systemName)
    }

    fun findAllByRegion(region: Region): List<Cart> {
        return cartRepository.findAllByRegion(region)
    }

    fun findAllByGameAndSystem(gameName: String, systemName: String): List<Cart> {
        return cartRepository.findAllByGameNameAndSystemName(gameName, systemName)
    }

    fun findAllByGameAndRegion(gameName: String, region: Region): List<Cart> {
        return cartRepository.findAllByGameNameAndRegion(gameName, region)
    }

    fun findAllBySystemAndRegion(systemName: String, region: Region): List<Cart> {
        return cartRepository.findAllBySystemNameAndRegion(systemName, region)
    }

    fun find(gameName: String, systemName: String, isEmulated: Boolean, region: Region, version: String): Cart {
        return cartRepository
            .findFirstByGameNameAndSystemNameAndSystemIsEmulatedAndRegionAndVersion(gameName, systemName, isEmulated, region, version)
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
