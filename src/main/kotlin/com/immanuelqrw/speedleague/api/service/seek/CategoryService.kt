package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.Category
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class CategoryService : BaseUniqueService<Category>(Category::class.java) {

    fun findByName(name: String): Category {
        return findAll(search = "name:$name").firstOrNull() ?: throw EntityNotFoundException()
    }

}
