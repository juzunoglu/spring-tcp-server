package com.jollify.chat.social.udp

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.channel.ExecutorChannel
import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter
import org.springframework.integration.ip.udp.UnicastSendingMessageHandler
import org.springframework.messaging.MessageChannel
import java.util.concurrent.Executors

@Configuration
class UdpConfig {

    @Bean
    fun udpInChannel() = ExecutorChannel(Executors.newCachedThreadPool())

    @Bean
    fun udpErrorChannel(): MessageChannel {
        return DirectChannel()
    }

    @Bean
    fun udpOutboundAdapter() = UnicastSendingMessageHandler("localhost", 11112)

    @Bean
    fun udpInboundAdapter() =
        UnicastReceivingChannelAdapter(11111).apply {
            outputChannel = udpInChannel()
            errorChannel = udpErrorChannel()
        }
}
