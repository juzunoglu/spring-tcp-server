package com.jollify.chat.social.tcp

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.channel.ExecutorChannel
import org.springframework.integration.ip.tcp.TcpInboundGateway
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory
import java.util.concurrent.Executors

@Configuration
class TcpServerConfig {
    private val port = 123

    @Bean
    fun tcpNetServerConnectionFactory(publisher: ApplicationEventPublisher): TcpNetServerConnectionFactory {
        return TcpNetServerConnectionFactory(port).apply {
            setApplicationEventPublisher(publisher)
        }
    }

    @Bean
    fun tcpInboundGateway(connectionFactory: TcpNetServerConnectionFactory): TcpInboundGateway {
        return TcpInboundGateway().apply {
            setConnectionFactory(connectionFactory)
            setRequestChannel(inputChannel())
            setErrorChannel(errorChannel())
        }
    }

    @Bean
    fun inputChannel() = ExecutorChannel(Executors.newCachedThreadPool())

    @Bean
    fun errorChannel() = DirectChannel()
}