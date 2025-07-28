package com.example.purchase_transactions.infrastructure.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Purchase Transaction")
                    .version("1.0")
                    .description("Apresenta a documentação do serviço Purchase Transaction para registros de Purchase. " +
                                 "Dispõe operações para submeter ou cancelar Purchase.")
            )
    }
}