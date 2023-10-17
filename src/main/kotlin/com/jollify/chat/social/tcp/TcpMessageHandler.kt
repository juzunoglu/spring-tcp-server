package com.jollify.chat.social.tcp

import com.jollify.chat.social.domain.entity.TcpMessage
import com.jollify.chat.social.domain.repository.TcpMessageRepository
import org.slf4j.LoggerFactory
import org.springframework.integration.annotation.MessageEndpoint
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.support.ErrorMessage
import org.springframework.util.ObjectUtils

@MessageEndpoint
class TcpMessageHandler(
    private val repository: TcpMessageRepository,
    private val registry: TcpConnectionsRegistry
) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @ServiceActivator(inputChannel = "inputChannel", async = "true", sendTimeout = "1000")
    suspend fun handleTcpRequest(input: ByteArray) {
        if (ObjectUtils.isEmpty(input)) return
        val content = String(input)
        log.info("Received message from TCP socket: {}", content)

        val tcpMessage = repository.save(TcpMessage(content = content))

        registry.broadcastMessage(tcpMessage.toString().toByteArray())

    }

    @ServiceActivator(inputChannel = "errorChannel")
    private suspend fun errorHandler(errorMessage: ErrorMessage) {
        log.error("Error occurred in integration flow")
    }
}
