package com.example.purchase_transactions.application.common

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.springframework.stereotype.Component

@Component
class DateTimeProvider {

    private val targetTimeZone = TimeZone.of("America/Fortaleza")

    /**
     * Calcula a data e hora atual no fuso horário 'America/Fortaleza'.
     *
     * @return Um objeto LocalDateTime representando a data e hora atual.
     */
    fun getLocalDateTimeNow(): LocalDateTime {
        val instant: Instant = Clock.System.now()
        return instant.toLocalDateTime(targetTimeZone)
    }

    /**
     * Verifica se a data e hora está no futuro em relação ao momento atual
     * no fuso horário 'America/Fortaleza'.
     *
     * @param date A data e hora a ser verificada.
     * @return true se a data estiver no futuro, false caso contrário.
     */
    fun isDateInFuture(date: LocalDateTime): Boolean {
        return date.toInstant(targetTimeZone) > Clock.System.now()
    }
}