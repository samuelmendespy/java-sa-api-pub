package com.example.purchase_transactions.infrastructure.messaging
import com.example.purchase_transactions.application.port.out.PurchaseMessageSenderPort
import com.example.purchase_transactions.infrastructure.dto.PurchaseCancellationMessage
import com.example.purchase_transactions.infrastructure.dto.ValidPurchaseMessage
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class PurchaseMessageRabbitMQAdapter(
    private val rabbitTemplate: RabbitTemplate,
    private val objectMapper: ObjectMapper
) : PurchaseMessageSenderPort {
    private val log = LoggerFactory.getLogger(PurchaseMessageRabbitMQAdapter::class.java)

    private val EXCHANGE_NAME = "purchases.exchange"
    private val ROUTING_KEY = "purchase.new"
    private val CANCELLATION_EXCHANGE_NAME = "purchases.cancellations.exchange"
    private val CANCELLATION_ROUTING_KEY = "purchase.cancellation"

    override fun sendValidPurchaseMessage(message: ValidPurchaseMessage) {
        try {
            val jsonMessage = objectMapper.writeValueAsString(message)
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, jsonMessage)
            log.info("Mensagem ValidPurchaseMessage enviada para RabbitMQ como JSON: {}", jsonMessage)
        } catch (e: Exception) {
            log.error("Erro ao enviar ValidPurchaseMessage para RabbitMQ: {}", e.message, e)
            throw RuntimeException("Falha ao enviar mensagem para RabbitMQ", e)
        }
    }

    override fun sendCancellationMessage(message: PurchaseCancellationMessage) {
        try {
            val jsonCancellationMessage = objectMapper.writeValueAsString(message)
            rabbitTemplate.convertAndSend(CANCELLATION_EXCHANGE_NAME, CANCELLATION_ROUTING_KEY, jsonCancellationMessage)
            log.info("Mensagem PurchaseCancellationMessage enviada para RabbitMQ como JSON: {}", jsonCancellationMessage)
        } catch (e: Exception) {
            log.error("Erro ao enviar ValidPurchaseMessage para RabbitMQ: {}", e.message, e)
            throw RuntimeException("Falha ao enviar mensagem para RabbitMQ", e)
        }
    }
}