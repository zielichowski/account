package com.zielichowski.exchange.infrastructure.provider

import com.zielichowski.commons.Currency
import com.zielichowski.commons.Rate
import com.zielichowski.exchange.domain.ExchangeRateProvider
import java.math.BigDecimal

internal class NbpRateProvider(private val nbpExchangeClient: NbpExchangeClient) : ExchangeRateProvider {

    override fun provideRate(currency: Currency): Rate {
        val midValue = nbpExchangeClient
                .getRate(currency.name)
                .rates[0]
                .mid
        return Rate(BigDecimal.valueOf(midValue))
    }
}