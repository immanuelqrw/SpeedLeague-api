package com.immanuelqrw.speedleague.api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.immanuelqrw.core.entity.BaseUniqueEntity
import com.immanuelqrw.speedleague.api.dto.output.Speedrun as SpeedrunOutput
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "Speedrun", uniqueConstraints = [UniqueConstraint(columnNames = ["categoryId", "cartId"])])
data class Speedrun(

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "cartId", referencedColumnName = "id", nullable = false)
    val cart: Cart,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "categoryId", referencedColumnName = "id", nullable = false)
    val category: Category

) : BaseUniqueEntity() {

    val output: SpeedrunOutput
        get() {
            return SpeedrunOutput(
                categoryName = category.name,
                gameName = cart.game.name,
                systemName = cart.system.name,
                isEmulated = cart.system.isEmulated,
                region = cart.region,
                version = cart.version
            )
        }

}
