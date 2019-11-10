package com.immanuelqrw.speedleague.api.service.seek

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class LeagueSpeedrunSeekServiceTest {

    @BeforeAll
    fun setUp() {}

    @Test
    fun findAllByLeague() {}

    @Test
    fun findAllByGame() {}

    @Test
    fun findAllBySystem() {}

    @Test
    fun findAllByRegion() {}

    @Test
    fun findAllByLeagueAndGame() {}

    @Test
    fun findAllByLeagueAndSystem() {}

    @Test
    fun findAllByLeagueAndRegion() {}

    @Test
    fun findAllByCategoryAndGame() {}

    @Test
    fun findAllByGameAndSystem() {}

    @Test
    fun findAllByGameAndRegion() {}

    @Test
    fun findAllBySystemAndRegion() {}

    @Test
    fun findByLeagueAndGameAndSystem() {}

    @Test
    fun findByLeagueAndGameAndRegion() {}

    @Test
    fun findByLeagueAndSystemAndRegion() {}

    @Test
    fun findByCategoryAndGameAndSystem() {}

    @Test
    fun findByCategoryAndGameAndRegion() {}

    @Test
    fun findByGameAndSystemAndRegion() {}

    @Test
    fun findAllByCategoryAndGameAndSystemAndRegion() {}

    @Test
    fun findAllByLeagueAndGameAndSystemAndRegion() {}

    @Test
    fun findAllByLeagueAndCategoryAndGameAndRegion() {}

    @Test
    fun find() {}

    @Test
    fun findAll() {}
}
