package com.jollify.chat.social.udp

import java.util.concurrent.ConcurrentHashMap
import com.jollify.chat.social.udp.Model.*
import org.springframework.stereotype.Component

@Component
class UdpRegistry {

    private val sessions: ConcurrentHashMap<String, Session> = ConcurrentHashMap()

    fun createSession(): Session {
        val sessionId = generateSessionId()
        val session = Session(sessionId)
        sessions[sessionId] = session
        return session
    }

    fun addPlayerToSession(sessionId: String, playerId: String, connection: PlayerConnection): Session? {
        val session = sessions[sessionId] ?: return null
        val player = Player(playerId, connection)

        session.player1
            ?.let { session.player2 = player } ?: run { session.player1 = player }

        return session
    }

    fun getSession(sessionId: String): Session? = sessions[sessionId]

    private fun generateSessionId(): String {
        return System.currentTimeMillis().toString()
    }
}