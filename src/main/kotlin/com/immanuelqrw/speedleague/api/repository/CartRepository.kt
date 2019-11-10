package com.immanuelqrw.speedleague.api.repository

import com.immanuelqrw.core.api.repository.BaseUniqueRepository
import com.immanuelqrw.speedleague.api.entity.Cart
import com.immanuelqrw.speedleague.api.entity.Region
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : BaseUniqueRepository<Cart> {

    fun findAllByGameNameAndRemovedOnIsNull(name: String): List<Cart>

    fun findAllBySystemNameAndRemovedOnIsNull(name: String): List<Cart>

    fun findAllByRegionAndRemovedOnIsNull(region: Region): List<Cart>

    fun findAllByGameNameAndSystemNameAndRemovedOnIsNull(gameName: String, systemName: String): List<Cart>

    fun findAllByGameNameAndRegionAndRemovedOnIsNull(gameName: String, region: Region): List<Cart>

    fun findAllBySystemNameAndRegionAndRemovedOnIsNull(systemName: String, region: Region): List<Cart>

    fun findFirstByGameNameAndSystemNameAndSystemIsEmulatedAndRegionAndVersionAndRemovedOnIsNull(gameName: String, systemName: String, isEmulated: Boolean, region: Region, version: String): Cart?

    fun findAllByGameNameAndSystemNameAndSystemIsEmulatedAndRegionAndVersionAndRemovedOnIsNull(gameName: String?, systemName: String?, isEmulated: Boolean?, region: Region?, version: String?): List<Cart>

}
