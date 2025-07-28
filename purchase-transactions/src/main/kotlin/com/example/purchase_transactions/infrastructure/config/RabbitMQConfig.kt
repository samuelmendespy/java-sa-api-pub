package com.example.purchase_transactions.infrastructure.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.core.Queue
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig (
    @Value("\${app.rabbitmq.exchange.name}")
    private val EXCHANGE_NAME: String,
    @Value("\${app.rabbitmq.routing.key}")
    private val ROUTING_KEY: String,
    @Value("\${app.rabbitmq.queue.name}")
    private val QUEUE_NAME: String,

    @Value("\${app.rabbitmq.exchange.name.cancellation}")
    private val CANCELLATION_EXCHANGE_NAME : String,
    @Value("\${app.rabbitmq.routing.key.cancellation}")
    private val CANCELLATION_ROUTING_KEY : String,
    @Value("\${app.rabbitmq.queue.name.cancellation}")
    private val CANCELLATION_QUEUE_NAME : String

){

    // Beans para purchases
    @Bean
    fun purchaseTopicExchange(): TopicExchange {
        return TopicExchange(EXCHANGE_NAME)
    }

    @Bean
    fun purchaseQueue(): Queue {
        return Queue(QUEUE_NAME, true, false, false)
    }

    @Bean
    fun purchaseBinding(purchaseQueue: Queue, purchaseTopicExchange: TopicExchange): Binding {
        return BindingBuilder.bind(purchaseQueue)
            .to(purchaseTopicExchange)
            .with(ROUTING_KEY)
    }

    // --- Beans para cancellations ---

    @Bean
    fun cancellationTopicExchange(): TopicExchange {
        return TopicExchange(CANCELLATION_EXCHANGE_NAME)
    }

    @Bean
    fun cancellationQueue(): Queue {
        return Queue(CANCELLATION_QUEUE_NAME, true, false, false)
    }

    @Bean
    fun cancellationBinding(cancellationQueue: Queue, cancellationTopicExchange: TopicExchange): Binding {
        return BindingBuilder.bind(cancellationQueue)
            .to(cancellationTopicExchange)
            .with(CANCELLATION_ROUTING_KEY)
    }
}