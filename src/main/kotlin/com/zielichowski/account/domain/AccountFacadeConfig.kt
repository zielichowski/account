package com.zielichowski.account.domain

import com.zielichowski.exchange.domain.MoneyExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class AccountFacadeConfig {

    @Bean
    fun accountFacade(accountRepository: AccountRepository, moneyExchange: MoneyExchange): AccountFacade {
        return AccountFacade(accountRepository, moneyExchange)
    }

}