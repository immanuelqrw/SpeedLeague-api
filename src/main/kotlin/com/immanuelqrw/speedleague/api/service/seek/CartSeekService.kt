package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.Cart
import com.immanuelqrw.speedleague.api.entity.Region
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class CartSeekService : BaseUniqueService<Cart>(Cart::class.java) {

    fun findAllByGame(gameName: String): List<Cart> {
        return findAll().filter { cart ->
            cart.game.name == gameName
        }
    }

    fun findAllBySystem(systemName: String): List<Cart> {
        return findAll().filter { cart ->
            cart.system.name == systemName
        }
    }

    fun findAllByRegion(region: Region): List<Cart> {
        return findAll().filter { cart ->
            cart.region == region
        }
    }

    fun findAllByGameAndSystem(gameName: String, systemName: String): List<Cart> {
        return findAll().filter { cart ->
            cart.game.name == gameName && cart.system.name == systemName
        }
    }

    fun findAllByGameAndRegion(gameName: String, region: Region): List<Cart> {
        return findAll().filter { cart ->
            cart.game.name == gameName && cart.region == region
        }
    }

    fun findAllBySystemAndRegion(systemName: String, region: Region): List<Cart> {
        return findAll().filter { cart ->
            cart.system.name == systemName && cart.region == region
        }
    }

    fun find(gameName: String, systemName: String, isEmulated: Boolean, region: Region, version: String): Cart {
        return findAll().firstOrNull { cart ->
            cart.game.name == gameName &&
            cart.system.name == systemName &&
            cart.system.isEmulated == isEmulated &&
            cart.region == region &&
            cart.version == version
        } ?: throw EntityNotFoundException()
    }

    fun findAll(gameName: String?, systemName: String?, isEmulated: Boolean?, region: Region?, version: String?): List<Cart> {
        return findAll()
            .filter { cart ->  gameName?.let { cart.game.name == gameName } ?: true }
            .filter { cart ->  systemName?.let { cart.system.name == systemName } ?: true }
            .filter { cart ->  isEmulated?.let { cart.system.isEmulated == isEmulated } ?: true }
            .filter { cart ->  region?.let { cart.region == region } ?: true }
            .filter { cart ->  version?.let { cart.version == version } ?: true }
    }

}
