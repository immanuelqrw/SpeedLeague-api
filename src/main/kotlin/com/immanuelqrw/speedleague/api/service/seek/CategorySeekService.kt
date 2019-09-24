package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.Category
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class CategorySeekService : BaseUniqueService<Category>(Category::class.java) {

    fun findByName(name: String): Category {
        return findAllActive(search = "name:$name").firstOrNull() ?: throw EntityNotFoundException()
    }

}
