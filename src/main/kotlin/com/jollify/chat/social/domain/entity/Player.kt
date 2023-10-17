package com.jollify.chat.social.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "player")
data class Player(
    @Id val id: String? = null,
    val name: String,
    val score: Int
)
