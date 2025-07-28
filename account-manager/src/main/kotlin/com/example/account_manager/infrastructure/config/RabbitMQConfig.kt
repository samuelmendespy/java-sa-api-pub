package com.example.account_manager.infrastructure.config

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig(
    @Value("\${app.rabbitmq.exchange.name}")
    private val exchangeName: String,
    @Value("\${app.rabbitmq.routing.key}")
    private val routingKey: String,
    @Value("\${app.rabbitmq.queue.name}")
    private val queueName: String
) {

    @Bean
    fun jsonMessageConverter(): MessageConverter {
        // This converter is crucial for handling JSON messages
        // Ensure Jackson is configured to handle kotlinx.datetime if needed
        return Jackson2JsonMessageConverter()
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): AmqpTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = jsonMessageConverter()
        return rabbitTemplate
    }

    @Bean
    fun exchange(): TopicExchange {
        // Using TopicExchange for flexible routing based on routing keys
        return TopicExchange(exchangeName)
    }

    @Bean
    fun queue(): Queue {
        // Declare the queue that this service will listen to
        return Queue(queueName, true) // Durable queue
    }

    @Bean
    fun binding(queue: Queue, exchange: TopicExchange): Binding {
        // Bind the queue to the exchange with the specific routing key
        return BindingBuilder.bind(queue).to(exchange).with(routingKey)
    }
}