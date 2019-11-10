package com.immanuelqrw.speedleague.api.service.seek

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SpeedrunSeekServiceTest {

    @BeforeAll
    fun setUp() {}

    @Test
    fun findAllByGame() {}

    @Test
    fun findAllBySystem() {}

    @Test
    fun findAllByRegion() {}

    @Test
    fun findAllByCategoryAndGame() {}

    @Test
    fun findAllByGameAndSystem() {}

    @Test
    fun findAllByGameAndRegion() {}

    @Test
    fun findAllBySystemAndRegion() {}

    @Test
    fun findByCategoryAndGameAndSystem() {}

    @Test
    fun findByCategoryAndGameAndRegion() {}

    @Test
    fun findByGameAndSystemAndRegion() {}

    @Test
    fun find() {}

    @Test
    fun findAll() {}
}
