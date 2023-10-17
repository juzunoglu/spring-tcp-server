package com.jollify.chat.social.domain.repository

import com.jollify.chat.social.domain.entity.TcpMessage
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TcpMessageRepository: CoroutineCrudRepository<TcpMessage, String> {
}