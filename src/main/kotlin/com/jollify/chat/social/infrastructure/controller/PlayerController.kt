package com.jollify.chat.social.infrastructure.controller

import com.jollify.chat.social.domain.entity.Player
import com.jollify.chat.social.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class PlayerController(
    private val playerRepository: PlayerRepository
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @PostMapping(path = ["api/v1/create"])
    suspend fun createPlayer(@RequestBody player: Player): Player {
        log.info("createPlayer is called(player = {})", player)
        return playerRepository.save(player)
    }

    @GetMapping(path = ["api/v1/retrieve"])
    fun getAllPlayers(): Flow<Player> {
        log.info("getAllPlayers is called")
        return playerRepository.findAll()
    }
}