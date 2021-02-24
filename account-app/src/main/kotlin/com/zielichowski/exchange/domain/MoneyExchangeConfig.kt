package com.zielichowski.exchange.domain

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class MoneyExchangeConfig {
    @Bean
    fun moneyExchange(exchangeRateProvider: ExchangeRateProvider): MoneyExchange {
        return NbpMoneyExchange(exchangeRateProvider)
    }
}