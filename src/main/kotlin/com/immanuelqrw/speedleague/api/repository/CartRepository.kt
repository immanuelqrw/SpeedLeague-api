package com.immanuelqrw.speedleague.api.repository

import com.immanuelqrw.core.api.repository.BaseUniqueRepository
import com.immanuelqrw.speedleague.api.entity.Cart
import com.immanuelqrw.speedleague.api.entity.Region
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : BaseUniqueRepository<Cart> {

    fun findAllByGameName(name: String): List<Cart>

    fun findAllBySystemName(name: String): List<Cart>

    fun findAllByRegion(region: Region): List<Cart>

    fun findAllByGameNameAndSystemName(gameName: String, systemName: String): List<Cart>

    fun findAllByGameNameAndRegion(gameName: String, region: Region): List<Cart>

    fun findAllBySystemNameAndRegion(systemName: String, region: Region): List<Cart>

    fun findFirstByGameNameAndSystemNameAndSystemIsEmulatedAndRegionAndVersion(gameName: String, systemName: String, isEmulated: Boolean, region: Region, version: String): Cart?

    fun findAllByGameNameAndSystemNameAndSystemIsEmulatedAndRegionAndVersion(gameName: String?, systemName: String?, isEmulated: Boolean?, region: Region?, version: String?): List<Cart>

}
