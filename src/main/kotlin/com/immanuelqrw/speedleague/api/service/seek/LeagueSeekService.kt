package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.dto.update.LeagueDivisionShift
import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.exception.LeagueHasEndedException
import com.immanuelqrw.speedleague.api.repository.LeagueRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.persistence.EntityNotFoundException

@Service
class LeagueSeekService : BaseUniqueService<League>(League::class.java) {

    @Autowired
    private lateinit var leagueRepository: LeagueRepository

    @PreAuthorize("hasRole('ADMIN')")
    @PostFilter("filterObject.relatedRunners.contains(authentication.name)")
    override fun findAllActive(page: Pageable?, search: String?): Iterable<League> {
        return super.findAllActive(page, search)
    }

    // ? Consider moving to LeagueService
    fun validateLeagueChange(endedOn: LocalDateTime?, failureMessage: String = "League has ended") {
        // If league has ended, do not allow changes/creation
        endedOn?.run {
            throw LeagueHasEndedException(failureMessage)
        }
    }

    // ? Consider moving to LeagueService
    fun endLeague(league: League, endedOn: LocalDateTime?) {
        validateLeagueChange(league.endedOn)

        league.endedOn ?: run {
            league.endedOn = endedOn
            super.create(league)
        }
    }

    override fun create(entity: League): League {
        val endedOn: LocalDateTime? = entity.endedOn
        validateLeagueChange(endedOn, "League ${entity.name} has ended on [$endedOn] and no changes can be made")

        return super.create(entity)
    }

    fun find(name: String, season: Int, tierLevel: Int): League {
        return leagueRepository.findByNameAndSeasonAndTierLevelAndRemovedOnIsNull(name, season, tierLevel)
            ?: throw EntityNotFoundException()
    }

    fun findAllTiers(name: String, season: Int): List<League> {
        return leagueRepository.findAllByNameAndSeasonAndRemovedOnIsNullOrderByTierLevelAsc(name, season)
    }

    fun findBottomLeague(name: String, season: Int): League {
        return leagueRepository.findFirstByNameAndSeasonAndRemovedOnIsNullOrderByTierLevelDesc(name, season)
            ?: throw EntityNotFoundException()
    }

    // ? Consider moving to LeagueService
    fun updateDivisionShifts(leagueDivisionShift: LeagueDivisionShift): League {
        val modifiedLeague: League = leagueDivisionShift.run {
            val mainLeague: League = find(leagueName, season, tierLevel)

            promotions?.let {
                val promotedLeague: League = find(leagueName, season, tierLevel - 1)
                mainLeague.promotions = promotions
                promotedLeague.relegations = promotions

                create(promotedLeague)
            }

            relegations?.let {
                val relegatedLeague: League = find(leagueName, season, tierLevel + 1)
                mainLeague.relegations = relegations
                relegatedLeague.promotions = relegations

                create(relegatedLeague)
            }

            mainLeague
        }

        return create(modifiedLeague)
    }

}
