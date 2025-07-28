package com.example.purchase_transactions.infrastructure.controller

import com.example.purchase_transactions.application.PurchaseService
import com.example.purchase_transactions.application.common.DateTimeProvider
import com.example.purchase_transactions.infrastructure.dto.CancelPurchaseRequest
import com.example.purchase_transactions.infrastructure.dto.ProcessPurchaseRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/purchase")
class PurchaseController(
    private val purchaseService: PurchaseService,
    private val dateTimeProvider: DateTimeProvider
) {
    private val log = LoggerFactory.getLogger(PurchaseController::class.java)

    @Operation(description = "Endpoint para enviar uma compra para processamento")
    @PostMapping(
        consumes = [MediaType.APPLICATION_XML_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun receberCompraXML(@RequestBody request: ProcessPurchaseRequest): ResponseEntity<String> {
        log.info("Recebida requisição em XML de compra com nota fiscal de número {}", request.purchase.numberNota)

        if (request.purchase.id.isNullOrEmpty()) {
            log.warn("Tentativa de enviar compra sem número de Id.")
            return ResponseEntity.badRequest().body("""{"message": "ID de transação da compra é obrigatório."}""")
        }

        if (request.purchase.numberNota.isNullOrEmpty()) {
            log.warn("Tentativa de enviar compra sem número da nota fiscal.")
            return ResponseEntity.badRequest().body("""{"message": "Número de nota fiscal da compra é obrigatório."}""")
        }

        return try {
            if (dateTimeProvider.isDateInFuture(request.purchase.purchaseTime)) {
                log.warn("Tentativa de eniar compra com 'purchaseTime' futuro para ID: {}. Horário enviado: {}",
                    request.purchase.id, request.purchase.purchaseTime)
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("""{"message": "O horário de compra não pode ser uma data/hora no futuro."}""")
            }

            val compra = request.purchase;
            purchaseService.processarCompra(compra)

            log.info("Compra processada com sucesso e enviada para RabbitMQ.")
            ResponseEntity.ok().body("""{"message": "Compra recebida e encaminhada com sucesso!"}""")
        } catch (e: Exception) {
            log.error("Erro ao processar a compra recebida: {}", e.message, e)
            // Retorna um erro 500 com uma mensagem de erro
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("""{"message": "Erro ao processar a compra: ${e.message}"}""")
        }
    }

    @Operation(description = "Endpoint para solicitar cancelar uma compra")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Transação deletadas com sucesso"),
            ApiResponse(responseCode = "400", description = "Erro de requisição"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @PutMapping(
        value = ["/cancel"],
        consumes = [MediaType.APPLICATION_XML_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun cancelarCompraXML(@RequestBody request: CancelPurchaseRequest): ResponseEntity<String> {
        log.info("Recebida requisição em XML para cancelar compra com nota fiscal de número {}", request.purchase.numberNota)

        if (dateTimeProvider.isDateInFuture(request.cancelRequestTime)) {
            log.warn("Tentativa de cancelar compra com 'cancelRequestTime' futuro para ID: {}. Horário enviado: {}",
                request.purchase.id, request.cancelRequestTime)
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body("""{"message": "O horário de cancelamento da compra não pode ser uma data/hora no futuro."}""")
        }

        return try {
            val isTokenValid : Boolean = if (request.token > 0) true else false

            if (!isTokenValid) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado")

            purchaseService.processarCancelamentoCompra( request.purchase, request.description, request.cancelRequestTime,)

            log.info("Compra processada com sucesso e enviada para RabbitMQ.")
            ResponseEntity.ok().body("""{"message": "Requisição de Cancelamento recebida e encaminhada com sucesso!"}""")
        } catch (e: Exception) {
            log.error("Erro ao processar a compra recebida: {}", e.message, e)
            // Retorna um erro 500 com uma mensagem de erro
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("""{"message": "Erro ao processar a compra: ${e.message}"}""")
        }
    }
}