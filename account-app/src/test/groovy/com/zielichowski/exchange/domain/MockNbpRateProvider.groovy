package com.zielichowski.exchange.domain

import com.zielichowski.commons.Currency
import com.zielichowski.commons.Rate
import org.jetbrains.annotations.NotNull

class MockNbpRateProvider implements ExchangeRateProvider {
    @Override
    Rate provideRate(@NotNull Currency currency) {
        if (currency == Currency.USD) {
            return new Rate(BigDecimal.valueOf(3.70))
        }
    }

}
