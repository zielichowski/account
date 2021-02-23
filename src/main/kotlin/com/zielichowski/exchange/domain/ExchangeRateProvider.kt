package com.zielichowski.exchange.domain

import com.zielichowski.commons.Currency
import com.zielichowski.commons.Rate

interface ExchangeRateProvider {
    fun provideRate(currency: Currency): Rate
}