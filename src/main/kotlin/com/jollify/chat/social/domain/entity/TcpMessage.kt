package com.jollify.chat.social.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class TcpMessage(
    @Id val id: String? = null,
    val content: String
) {

}
