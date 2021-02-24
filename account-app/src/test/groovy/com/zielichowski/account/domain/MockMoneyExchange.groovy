package com.zielichowski.account.domain

import com.zielichowski.commons.Currency
import com.zielichowski.commons.Money
import com.zielichowski.commons.Rate
import com.zielichowski.exchange.domain.MoneyExchange
import org.jetbrains.annotations.NotNull

class MockMoneyExchange implements MoneyExchange {

    @Override
    Money exchange(@NotNull Money source, @NotNull Currency destination) {
        if (destination == Currency.USD) {
            return source.exchangeTo(destination, new Rate(BigDecimal.valueOf(3.8)))
        }
        throw new UnsupportedOperationException()
    }
}
