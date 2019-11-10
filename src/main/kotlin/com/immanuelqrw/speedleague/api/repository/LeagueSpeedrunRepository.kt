package com.immanuelqrw.speedleague.api.repository

import com.immanuelqrw.core.api.repository.BaseUniqueRepository
import com.immanuelqrw.speedleague.api.entity.LeagueSpeedrun
import com.immanuelqrw.speedleague.api.entity.LeagueType
import com.immanuelqrw.speedleague.api.entity.Region
import org.springframework.stereotype.Repository

@Repository
interface LeagueSpeedrunRepository : BaseUniqueRepository<LeagueSpeedrun> {

    fun findAllBySpeedrunCartGameName(gameName: String): List<LeagueSpeedrun>

    fun findAllBySpeedrunCartSystemName(systemName: String): List<LeagueSpeedrun>

    fun findAllBySpeedrunCartRegion(region: Region): List<LeagueSpeedrun>

    fun findAllByLeagueNameAndSpeedrunCartGameName(leagueName: String, gameName: String): List<LeagueSpeedrun>

    fun findAllByLeagueNameAndSpeedrunCartSystemName(leagueName: String, systemName: String): List<LeagueSpeedrun>

    fun findAllByLeagueNameAndSpeedrunCartRegion(leagueName: String, region: Region): List<LeagueSpeedrun>

    fun findAllBySpeedrunCategoryAndSpeedrunCartGameName(category: String, gameName: String): List<LeagueSpeedrun>

    fun findAllBySpeedrunCartGameNameAndSpeedrunCartSystemName(gameName: String, systemName: String): List<LeagueSpeedrun>

    fun findAllBySpeedrunCartGameNameAndSpeedrunCartRegion(gameName: String, region: Region): List<LeagueSpeedrun>

    fun findAllBySpeedrunCartSystemNameAndSpeedrunCartRegion(systemName: String, region: Region): List<LeagueSpeedrun>

    fun findAllByLeagueNameAndSpeedrunCartGameNameAndSpeedrunCartSystemName(leagueName: String, gameName: String, systemName: String): List<LeagueSpeedrun>

    fun findAllByLeagueNameAndSpeedrunCartGameNameAndSpeedrunCartRegion(leagueName: String, gameName: String, region: Region): List<LeagueSpeedrun>

    fun findAllByLeagueNameAndSpeedrunCartSystemNameAndSpeedrunCartRegion(leagueName: String, systemName: String, region: Region): List<LeagueSpeedrun>

    fun findAllBySpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartSystemName(category: String, gameName: String, systemName: String): List<LeagueSpeedrun>

    fun findAllBySpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartRegion(category: String, gameName: String, region: Region): List<LeagueSpeedrun>

    fun findAllBySpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartRegion(gameName: String, systemName: String, region: Region): List<LeagueSpeedrun>

    fun findAllBySpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartRegion(category: String, gameName: String, systemName: String, region: Region): List<LeagueSpeedrun>

    fun findAllByLeagueNameAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartRegion(leagueName: String, gameName: String, systemName: String, region: Region): List<LeagueSpeedrun>

    fun findAllByLeagueNameAndSpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartRegion(leagueName: String, category: String, gameName: String, region: Region): List<LeagueSpeedrun>

    fun findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevel(leagueName: String, season: Int, tierLevel: Int): List<LeagueSpeedrun>

    fun findFirstByLeagueNameAndSpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartSystemIsEmulatedAndSpeedrunCartRegionAndSpeedrunCartVersion(leagueName: String, category: String, gameName: String, systemName: String, isEmulated: Boolean, region: Region, version: String): LeagueSpeedrun

    fun findAllByLeagueNameAndSpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartSystemIsEmulatedAndSpeedrunCartRegionAndSpeedrunCartVersion(leagueName: String, category: String, gameName: String, systemName: String, isEmulated: Boolean, region: Region, version: String): List<LeagueSpeedrun>

    fun findAllByLeagueNameAndLeagueTypeAndSpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartSystemIsEmulatedAndSpeedrunCartRegionAndSpeedrunCartVersion(leagueName: String?, leagueType: LeagueType?, category: String?, gameName: String?, systemName: String?, isEmulated: Boolean?, region: Region?, version: String?): List<LeagueSpeedrun>
}
