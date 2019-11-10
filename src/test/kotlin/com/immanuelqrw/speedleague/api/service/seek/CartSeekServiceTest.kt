package com.immanuelqrw.speedleague.api.service.seek

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CartSeekServiceTest {

    @BeforeAll
    fun setUp() {}

    @Test
    fun findAllByGame() {}

    @Test
    fun findAllBySystem() {}

    @Test
    fun findAllByRegion() {}

    @Test
    fun findAllByGameAndSystem() {}

    @Test
    fun findAllByGameAndRegion() {}

    @Test
    fun findAllBySystemAndRegion() {}

    @Test
    fun find() {}

    @Test
    fun findAll() {}
}
