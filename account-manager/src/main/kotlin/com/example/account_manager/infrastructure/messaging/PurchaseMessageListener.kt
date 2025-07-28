package com.example.account_manager.infrastructure.messaging

import com.example.account_manager.application.service.PurchaseService
import com.example.account_manager.infrastructure.dto.ValidPurchaseMessage
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class PurchaseMessageListener(
    private val purchaseService: PurchaseService
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    // The 'containerFactory' ensures proper message handling (e.g., concurrency, retries)
    @RabbitListener(queues = ["\${app.rabbitmq.queue.name}"])
    fun receiveValidPurchaseMessage(message: ValidPurchaseMessage) {
        logger.info("Received ValidPurchaseMessage: {}", message)

        try {
            // Extract the domain Purchase object from the message
            val domainPurchase = message.purchase

            // Use the PurchaseService (use case) to save the Purchase
            val savedPurchase = purchaseService.savePurchase(domainPurchase)
            logger.info("Purchase saved successfully with ID: {}", savedPurchase.id)

        } catch (e: Exception) {
            logger.error("Error processing ValidPurchaseMessage: {}", message, e)
            // Depending on your error handling strategy:
            // - Re-throw the exception to trigger RabbitMQ's retry mechanism (if configured)
            // - Publish to a Dead Letter Queue (DLQ)
            // - Log and swallow if message loss is acceptable for this error type
            throw e // Re-throwing ensures RabbitMQ listener marks the message as not acknowledged
        }
    }
}