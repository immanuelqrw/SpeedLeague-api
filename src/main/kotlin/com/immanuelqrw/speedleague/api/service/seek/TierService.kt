package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.Tier
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class TierService : BaseUniqueService<Tier>(Tier::class.java)
