package com.immanuelqrw.speedleague.api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.immanuelqrw.core.entity.BaseUniqueEntity
import com.immanuelqrw.speedleague.api.dto.output.LeagueSpeedrun as LeagueSpeedrunOutput
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "LeagueSpeedrun", uniqueConstraints = [UniqueConstraint(columnNames = ["leagueId", "speedrunId"])])
data class LeagueSpeedrun(

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "leagueId", referencedColumnName = "id", nullable = false)
    val league: League,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "speedrunId", referencedColumnName = "id", nullable = false)
    val speedrun: Speedrun

) : BaseUniqueEntity() {

    val output: LeagueSpeedrunOutput
        get() {
            return LeagueSpeedrunOutput(
                leagueName = league.name,
                season = league.season,
                tierLevel = league.tier.level,
                tierName = league.tier.name,
                categoryName = speedrun.category.name,
                gameName = speedrun.cart.game.name,
                systemName = speedrun.cart.system.name,
                isEmulated = speedrun.cart.system.isEmulated,
                region = speedrun.cart.region,
                version = speedrun.cart.version
            )
        }

}
