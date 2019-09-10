package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.PlayoffRule
import org.springframework.stereotype.Service

@Service
class PlayoffRuleService : BaseUniqueService<PlayoffRule>(PlayoffRule::class.java)
