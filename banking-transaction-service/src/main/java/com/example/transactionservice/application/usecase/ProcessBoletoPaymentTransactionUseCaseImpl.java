package com.example.transactionservice.application.usecase;

import com.example.transactionservice.application.command.ProcessBoletoPaymentCommand;
import com.example.transactionservice.application.dto.BankAuthorizationRequest;
import com.example.transactionservice.application.dto.BankAuthorizationResponse;
import com.example.transactionservice.application.result.TransactionProcessingResult;
import com.example.transactionservice.domain.enums.PayMethodType;
import com.example.transactionservice.domain.enums.TransactionType;
import com.example.transactionservice.domain.model.Transaction;
import com.example.transactionservice.domain.port.out.BankGateway;
import com.example.transactionservice.domain.port.out.EventPublisher;
import com.example.transactionservice.domain.port.out.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // Marca como um componente Spring para injeção de dependência
@RequiredArgsConstructor // Gera um construtor com os campos final, útil para injeção
public class ProcessBoletoPaymentTransactionUseCaseImpl implements ProcessBoletoPaymentTransactionUseCase {

    private final TransactionRepository transactionRepository;
    private final BankGateway bankGateway;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional // Garante que a operação seja atômica no banco de dados local
    public TransactionProcessingResult execute(ProcessBoletoPaymentCommand command) {
        // 1. Criar a transação inicial no nosso banco de dados com status PENDING
        Transaction transaction = Transaction.newPendingTransaction(
                command.getAccountId(),
                command.getAmount(),
                TransactionType.CREDIT,
                PayMethodType.BOLETO,
                command.getBoletoNumber()
        );

        // Salar a transação gerando o transaction.id
        transaction = transactionRepository.save(transaction); // Salva para obter o ID gerado (se for o caso)

        // 2. Preparar a requisição para o Bank Gateway
        BankAuthorizationRequest bankRequest = BankAuthorizationRequest.builder()
                .transactionId(transaction.getId())
                .amount(command.getAmount())
                .paymentMethod(PayMethodType.BOLETO.name())
                .accountId(command.getAccountId().toString()) // Ou um ID de conta específico do banco
                .boletoNumber(command.getBoletoNumber())
                .expirationDate(command.getExpirationDate())
                .recipientInfo(command.getRecipientInfo())
                .build();

        // 3. Chamar o Bank System (BankGateway) - ESTA É A CHAMADA SÍNCRONA CRÍTICA
        BankAuthorizationResponse bankResponse;
        try {
            bankResponse = bankGateway.authorizeAndDebit(bankRequest);
        } catch (Exception e) {
            // Em caso de falha na comunicação com o banco, marcar transação como FAILED
            transaction.fail("Communication error with Bank: " + e.getMessage());
            transactionRepository.save(transaction); // Salva o status atualizado
            eventPublisher.publishGenericTransactionStatusChangedEvent(transaction.getId(), transaction.getStatus(), transaction.getRejectionReason());
            return TransactionProcessingResult.failed(transaction.getId(), "Bank communication error: " + e.getMessage());
        }

        // 4. Processar a resposta do Bank
        if (bankResponse.isApproved()) {
            transaction.approve(bankResponse.getAuthorizationCode(), bankResponse.getBankTransactionId());
            transactionRepository.save(transaction); // Atualiza o status e dados no nosso banco

            // Publicar evento para serviços de pós-processamento (assíncrono)
            eventPublisher.publishTransactionApprovedEvent(
                    transaction.getId(),
                    bankResponse.getAuthorizationCode()
            );
            eventPublisher.publishGenericTransactionStatusChangedEvent(transaction.getId(), transaction.getStatus(), "Transaction approved by bank.");

            // Em um sistema real, o sucesso da transação final (SUCCESS) viria de um serviço que processa o evento de aprovação
            // Por simplicidade aqui, vamos considerar que após a aprovação do banco, a transação está 'sucedida' internamente
            transaction.markAsSuccess();
            transactionRepository.save(transaction);
            eventPublisher.publishTransactionSuccessEvent(transaction.getId());
            eventPublisher.publishGenericTransactionStatusChangedEvent(transaction.getId(), transaction.getStatus(), "Transaction marked as success.");


            return TransactionProcessingResult.success(
                    transaction.getId(),
                    bankResponse.getAuthorizationCode(),
                    bankResponse.getBankTransactionId()
            );
        } else {
            // Transação rejeitada pelo banco
            transaction.reject(bankResponse.getRejectionReason());
            transactionRepository.save(transaction); // Atualiza o status e razão de rejeição

            // Publicar evento para transação rejeitada
            eventPublisher.publishTransactionRejectedEvent(
                    transaction.getId(),
                    bankResponse.getRejectionReason()
            );
            eventPublisher.publishGenericTransactionStatusChangedEvent(transaction.getId(), transaction.getStatus(), transaction.getRejectionReason());

            return TransactionProcessingResult.rejected(
                    transaction.getId(),
                    bankResponse.getRejectionReason()
            );
        }
    }
}