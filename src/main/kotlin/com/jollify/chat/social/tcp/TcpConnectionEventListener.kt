package com.jollify.chat.social.tcp

import org.springframework.context.event.EventListener
import org.springframework.integration.ip.tcp.connection.*
import org.springframework.stereotype.Component

@Component
class TcpConnectionEventListener(
    private val registry: TcpConnectionsRegistry
) {

    @EventListener
    fun handleOpenConnection(event: TcpConnectionOpenEvent) {
        println("New connection established event: ${event.connectionId}")
        val connection = event.source as TcpConnection
        registry.addConnection(event.connectionId, connection)
    }

    @EventListener
    fun handleCloseConnection(event: TcpConnectionCloseEvent) {
        println("Connection closed event: ${event.connectionFactoryName}")
        registry.removeConnection(event.connectionId)
    }

    @EventListener
    fun handleConnectionException(event: TcpConnectionExceptionEvent) {
        println(message = "Exception occurred on connection: ${event.connectionFactoryName}")
    }
}