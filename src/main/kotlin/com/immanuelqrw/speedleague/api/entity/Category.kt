package com.immanuelqrw.speedleague.api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.immanuelqrw.core.entity.BaseUniqueEntity
import com.immanuelqrw.speedleague.api.dto.output.Category as CategoryOutput
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "Category")
data class Category(

    @Column(name = "name", unique = true, nullable = false)
    val name: String

) : BaseUniqueEntity() {

    val output: CategoryOutput
        get() {
            return CategoryOutput(
                name = name
            )
        }

}
