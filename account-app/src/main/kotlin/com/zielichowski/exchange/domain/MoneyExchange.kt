package com.zielichowski.exchange.domain

import com.zielichowski.commons.Currency
import com.zielichowski.commons.Money


interface MoneyExchange {
    fun exchange(source: Money, destination: Currency): Money
}
