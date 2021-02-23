package com.zielichowski.account.domain

import com.zielichowski.commons.Currency
import com.zielichowski.commons.Money
import com.zielichowski.commons.Rate
import com.zielichowski.exchange.domain.MoneyExchange
import java.math.BigDecimal

internal class TestMoneyExchange : MoneyExchange {
    override fun exchange(source: Money, destination: Currency): Money {
        if (destination == Currency.USD) {
            return source.exchangeTo(destination, Rate(BigDecimal.valueOf(3.8)))
        }
        throw UnsupportedOperationException()
    }
}