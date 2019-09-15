package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.Cart
import com.immanuelqrw.speedleague.api.entity.Region
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class CartService : BaseUniqueService<Cart>(Cart::class.java) {

    fun findAllByGame(gameName: String): List<Cart> {
        return findAll().filter { cart ->
            cart.game.name == gameName
        }
    }

    fun findAllBySystem(systemName: String): List<Cart> {
        return findAll().filter { cart ->
            cart.distinctSystem.system.name == systemName
        }
    }

    fun findAllByRegion(region: Region): List<Cart> {
        return findAll().filter { cart ->
            cart.distinctSystem.region == region
        }
    }

    fun findAllByGameAndSystem(gameName: String, systemName: String): List<Cart> {
        return findAll().filter { cart ->
            cart.game.name == gameName && cart.distinctSystem.system.name == systemName
        }
    }

    fun findAllByGameAndRegion(gameName: String, region: Region): List<Cart> {
        return findAll().filter { cart ->
            cart.game.name == gameName && cart.distinctSystem.region == region
        }
    }

    fun findAllBySystemAndRegion(systemName: String, region: Region): List<Cart> {
        return findAll().filter { cart ->
            cart.distinctSystem.system.name == systemName && cart.distinctSystem.region == region
        }
    }

    fun findByGameAndSystemAndRegion(gameName: String, systemName: String, region: Region): Cart {
        return findAll().firstOrNull { cart ->
            cart.game.name == gameName &&
            cart.distinctSystem.system.name == systemName &&
            cart.distinctSystem.region == region
        } ?: throw EntityNotFoundException()
    }

    fun findAll(gameName: String?, systemName: String?, isEmulated: Boolean?, region: Region?, versionName: String?): List<Cart> {
        return findAll()
            .filter { cart ->  gameName?.let { cart.game.name == gameName } ?: true }
            .filter { cart ->  systemName?.let { cart.distinctSystem.system.name == systemName } ?: true }
            .filter { cart ->  isEmulated?.let { cart.distinctSystem.system.isEmulated == isEmulated } ?: true }
            .filter { cart ->  region?.let { cart.distinctSystem.region == region } ?: true }
            .filter { cart ->  versionName?.let { cart.distinctSystem.version.name == versionName } ?: true }
    }

}
