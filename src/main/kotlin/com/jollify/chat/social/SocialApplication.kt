package com.jollify.chat.social

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.net.DatagramSocket
import java.net.InetAddress

@SpringBootApplication
@OpenAPIDefinition(info = Info(title = "APIs", version = "1.0", description = "TCP and UDP Server Demo"))
class SocialApplication {
    @Bean
    fun udpClientRunner(): CommandLineRunner {
        return CommandLineRunner {
            val client1 = UdpClient("player1")
            //val client2 = UdpClient("player2")

            repeat(50_000) {
                client1.sendMessage("12345", "jump")
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SocialApplication>(*args)
}


class UdpClient(private val clientId: String) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    private val serverAddress: InetAddress = InetAddress.getByName("localhost")
    private val serverPort: Int = 11111
    private val clientSocket: DatagramSocket = DatagramSocket()

    fun sendMessage(sessionId: String, gameMessage: String) {
        val message = "$sessionId:$clientId:$gameMessage"
        val buffer = message.toByteArray()
        val packet = java.net.DatagramPacket(buffer, buffer.size, serverAddress, serverPort)

        clientSocket.send(packet)
        //log.info("Sent message $clientId: $message")
    }
}