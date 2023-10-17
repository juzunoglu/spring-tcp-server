package com.jollify.chat.social.udp

class Model {
    data class Player(val playerId: String, val connection: PlayerConnection)
    data class PlayerConnection(val ip: String, val port: Int)
    data class Session(val sessionId: String, var player1: Player? = null, var player2: Player? = null) {
        fun isFull() = player1 != null && player2 != null
    }
}