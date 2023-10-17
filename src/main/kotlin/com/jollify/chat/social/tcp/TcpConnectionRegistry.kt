package com.jollify.chat.social.tcp

import org.springframework.integration.ip.tcp.connection.TcpConnection
import org.springframework.integration.support.MessageBuilder
import org.springframework.stereotype.Component
import org.springframework.util.ObjectUtils
import java.util.concurrent.ConcurrentHashMap

@Component
class TcpConnectionsRegistry {

    private val connections: MutableMap<String, TcpConnection> = ConcurrentHashMap()

    fun addConnection(connectionId: String, connection: TcpConnection) {
        connections[connectionId] = connection
    }

    fun removeConnection(connectionId: String) {
        connections.remove(connectionId)
    }

    fun getOpenConnections(): Collection<TcpConnection> {
        return connections.filterValues { it.isOpen }.values
    }

    suspend fun broadcastMessage(message: ByteArray) {
        val messageObj = MessageBuilder.withPayload(message).build()
        if (!ObjectUtils.isEmpty(messageObj)) {
            getOpenConnections().forEach { tcpConnection -> tcpConnection.send(messageObj) }
        } else {
            println(message = "MessageObj is null or empty")
        }
    }
}
