package com.zielichowski.exchange.domain

import com.zielichowski.commons.Currency
import com.zielichowski.commons.Money


internal class NbpMoneyExchange(private val exchangeRateProvider: ExchangeRateProvider) : MoneyExchange {
    override fun exchange(source: Money, destination: Currency): Money {
        return source.exchangeTo(destination, exchangeRateProvider.provideRate(destination))
    }
}