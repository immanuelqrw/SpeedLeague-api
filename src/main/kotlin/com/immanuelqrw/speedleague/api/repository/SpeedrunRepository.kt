package com.immanuelqrw.speedleague.api.repository

import com.immanuelqrw.core.api.repository.BaseUniqueRepository
import com.immanuelqrw.speedleague.api.entity.Region
import com.immanuelqrw.speedleague.api.entity.Speedrun
import org.springframework.stereotype.Repository

@Repository
interface SpeedrunRepository : BaseUniqueRepository<Speedrun> {

    fun findAllByCartGameName(gameName: String): List<Speedrun>

    fun findAllByCartSystemName(systemName: String): List<Speedrun>

    fun findAllByCartRegion(region: Region): List<Speedrun>

    fun findAllByCategoryAndCartGameName(category: String, gameName: String): List<Speedrun>

    fun findAllByCartGameNameAndCartSystemName(gameName: String, systemName: String): List<Speedrun>

    fun findAllByCartGameNameAndCartRegion(gameName: String, region: Region): List<Speedrun>

    fun findAllByCartSystemNameAndCartRegion(systemName: String, region: Region): List<Speedrun>

    fun findAllByCategoryAndCartGameNameAndCartSystemName(category: String, gameName: String, systemName: String): List<Speedrun>

    fun findAllByCategoryAndCartGameNameAndCartRegion(category: String, gameName: String, region: Region): List<Speedrun>

    fun findAllByCartGameNameAndCartSystemNameAndCartRegion(gameName: String, systemName: String, region: Region): List<Speedrun>

    fun findAllByCategoryAndCartGameNameAndCartSystemNameAndCartSystemIsEmulatedAndCartRegion(category: String, gameName: String, systemName: String, isEmulated: Boolean, region: Region): List<Speedrun>

    fun findFirstByCategoryAndCartGameNameAndCartSystemNameAndCartSystemIsEmulatedAndCartRegionAndCartVersion(category: String, gameName: String, systemName: String, isEmulated: Boolean, region: Region, version: String): Speedrun?

    fun findAllByCategoryAndCartGameNameAndCartSystemNameAndCartSystemIsEmulatedAndCartRegionAndCartVersion(category: String?, gameName: String?, systemName: String?, isEmulated: Boolean?, region: Region?, version: String?): List<Speedrun>

}
