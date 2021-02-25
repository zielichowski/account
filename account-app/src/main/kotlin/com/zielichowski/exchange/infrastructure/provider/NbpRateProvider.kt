package com.zielichowski.exchange.infrastructure.provider

import com.zielichowski.commons.Currency
import com.zielichowski.commons.Rate
import com.zielichowski.exchange.domain.ExchangeRateProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.math.BigDecimal

internal class NbpRateProvider(private val nbpExchangeClient: NbpExchangeClient) : ExchangeRateProvider {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(NbpRateProvider::class.java)
    }

    override fun provideRate(currency: Currency): Rate {
        val midValue = nbpExchangeClient
            .getRate(currency.name)
            .rates[0]
            .mid
        logger.info("Got rate from nbp for currency: $currency, rate: $midValue")
        return Rate(BigDecimal.valueOf(midValue))
    }
}