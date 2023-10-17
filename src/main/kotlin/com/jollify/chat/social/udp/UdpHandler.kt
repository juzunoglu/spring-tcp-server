package com.jollify.chat.social.udp


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.integration.annotation.MessageEndpoint
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.ip.IpHeaders
import org.springframework.integration.ip.udp.UnicastSendingMessageHandler
import org.springframework.integration.support.MessageBuilder
import org.springframework.messaging.Message

@MessageEndpoint
class UdpHandler(
    private val udpRegistry: UdpRegistry,
    private val udpOutboundAdapter: UnicastSendingMessageHandler
) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
    @ServiceActivator(inputChannel = "udpInChannel")
    suspend fun handleMessage(message: Message<ByteArray>) {
        withContext(Dispatchers.IO) {
            val payload = String(message.payload)
            log.info("Received: $payload")

            val parts = payload.split(":")
            if (parts.size != 3) {
                log.info("Invalid message format")
                return@withContext
            }

            val sessionId = parts[0]
            val playerId = parts[1]
            val gameMessage = parts[2]

            val session = udpRegistry.getSession(sessionId) ?: return@withContext
            if (session.player1?.playerId != playerId && session.player2?.playerId != playerId) {
                // Player is not yet added to the session. Add them with their connection details.
                val playerConnection = Model.PlayerConnection(
                    message.headers["ip_address"] as String,
                    message.headers["port"] as Int
                )
                udpRegistry.addPlayerToSession(sessionId, playerId, playerConnection)
            }

            if (session.player1?.playerId == playerId) session.player2?.playerId else session.player1?.playerId
            val otherPlayerConnection =
                if (session.player1?.playerId == playerId) session.player2?.connection else session.player1?.connection

            udpOutboundAdapter.handleMessage(
                MessageBuilder.withPayload(gameMessage)
                    .setHeader(IpHeaders.IP_ADDRESS, otherPlayerConnection?.ip)
                    .setHeader(IpHeaders.PORT, otherPlayerConnection?.port)
                    .build()
            )
        }
    }
}
