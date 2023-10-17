package com.jollify.chat.social.domain.repository

import com.jollify.chat.social.domain.entity.Player
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PlayerRepository : CoroutineCrudRepository<Player, String> {

}