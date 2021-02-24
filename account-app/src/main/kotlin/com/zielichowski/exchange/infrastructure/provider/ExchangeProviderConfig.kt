package com.zielichowski.exchange.infrastructure.provider

import com.zielichowski.exchange.domain.ExchangeRateProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Configuration
internal class ExchangeProviderConfig {

    @Bean
    fun restTemplate(): RestTemplate? {
        return RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMillis(500))
                .setReadTimeout(Duration.ofMillis(500))
                .errorHandler(NbpResponseErrorHandler())
                .build()
    }

    @Bean
    fun exchangeRateProvider(restTemplate: RestTemplate, @Value("\${nbp.api.template}") url: String): ExchangeRateProvider {
        return NbpRateProvider(NbpExchangeClient(restTemplate, url))
    }

}