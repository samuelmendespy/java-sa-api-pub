package com.example.account_manager.application.service

import com.example.account_manager.domain.model.Account
import com.example.account_manager.domain.model.port.AccountRepository
import org.springframework.stereotype.Service

import org.slf4j.LoggerFactory

@Service
class AccountService(
    private val accountRepository: AccountRepository
) {
    private val log = LoggerFactory.getLogger(AccountService::class.java)

    /**
     * Obter uma conta por Id
     *
     * @param id O id da conta a ser obtida.
     * @throws RuntimeException Se houver um erro ao serializar ou enviar a mensagem.
     */
    fun getAccountById(id: String) : Account? {
        log.info("AccountService: Foi recebido o pedido para obter a conta de id {}", id )
        return accountRepository.findById(id)
    }

    /**
     * Creditar uma conta por CPF
     *
     * @param cpf O CPF da conta a ser creditada.
     * @throws RuntimeException Se houver um erro ao serializar ou enviar a mensagem.
     */
    fun updateAccountPointsByCpf(accountId: String, operation: String) : Account? {
        val defaultVariation : Int = 1
        val existingAccount = accountRepository.findByCpf(accountId)
        var newPoints : Int = existingAccount.points

        if (operation.equals("credit") ) newPoints += defaultVariation

        if (operation.equals("debit") ) newPoints -= defaultVariation

        return existingAccount.let {
                val accountToSave = Account (
                    id = existingAccount.id,
                    cpf = existingAccount.cpf,
                    points = newPoints
                    )

                accountRepository.save(accountToSave)
        }
    }
}
