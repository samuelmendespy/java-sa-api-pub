package com.example.purchase_transactions.application

import com.example.purchase_transactions.application.common.DateTimeProvider
import com.example.purchase_transactions.application.port.out.PurchaseMessageSenderPort
import com.example.purchase_transactions.domain.model.Purchase
import com.example.purchase_transactions.infrastructure.dto.PurchaseCancellationMessage
import com.example.purchase_transactions.infrastructure.dto.ValidPurchaseMessage
import kotlinx.datetime.LocalDateTime
import org.springframework.stereotype.Service

import org.slf4j.LoggerFactory

import java.util.UUID

@Service
class PurchaseService(
    private val purchaseMessageSender: PurchaseMessageSenderPort,
    private val dateTimeProvider: DateTimeProvider
) {
    private val log = LoggerFactory.getLogger(PurchaseService::class.java)

    // Constantes para o RabbitMQ
    /**
     * Processa um objeto Purchase recebido, adiciona um ID de idempotência,
     * serializa para JSON e envia para o RabbitMQ.
     *
     * @param purchase O objeto Purchase já deserializado do XML.
     * @throws RuntimeException Se houver um erro ao serializar ou enviar a mensagem.
     */
    fun processarCompra(purchase: Purchase) {
        log.info("PurchaseService: Foi recebido o pedido para processar a compra de nota fiscal {}", purchase.numberNota )
        try {
            val validPurchase = ValidPurchaseMessage(purchase, dateTimeProvider.getLocalDateTimeNow())
            purchaseMessageSender.sendValidPurchaseMessage(validPurchase)

            log.info("Compra processada e mensagem enviada para o sistema de mensagens")
        } catch (e: Exception) {
            log.info("Erro ao processar e enviar compra da nota {} :  {}", purchase.numberNota, e.message)
            // TODO: Salvar com db
        }
    }

    /**
     * Recebe como parâmetros os dados para cancelamento de uma compra e envia Purchase recebido,
     * serializa para JSON e envia para o RabbitMQ.
     *
     * @param purchase O objeto Compra já deserializado do XML.
     * @param description Causa do cancelamento
     * @param cancelRequestTime Data e hora do pedido de cancelamento
     * @throws RuntimeException Se houver um erro ao serializar ou enviar a mensagem.
     */
    fun processarCancelamentoCompra(
        purchase: Purchase,
        description : String,
        cancelRequestTime: LocalDateTime
    ) {
        log.info("PurchaseService: Foi recebido o pedido para processar o cancelamento da compra de nota fiscal {}", purchase.numberNota )

        try {
            val cancellation = PurchaseCancellationMessage(UUID.randomUUID().toString(), purchase, description, cancelRequestTime, dateTimeProvider.getLocalDateTimeNow())
            purchaseMessageSender.sendCancellationMessage(cancellation)

            log.info("Pedido de Cancelamento enviado para o sitema de mensagens.")
        } catch (e: Exception) {
            log.info("Erro ao processar e enviar compra {}", e.message)
        }
    }
}