package com.zielichowski.exchange.domain

import com.zielichowski.commons.Currency
import com.zielichowski.commons.Money
import org.slf4j.Logger
import org.slf4j.LoggerFactory


internal class NbpMoneyExchange(private val exchangeRateProvider: ExchangeRateProvider) : MoneyExchange {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(NbpMoneyExchange::class.java)
    }

    override fun exchange(source: Money, destination: Currency): Money {
        val targetMoney = source.exchangeTo(destination, exchangeRateProvider.provideRate(destination))
        logger.info("Exchanged $source to $targetMoney with nbp exchange")
        return targetMoney
    }
}