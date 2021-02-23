package com.zielichowski.account.domain

import com.zielichowski.commons.Currency
import com.zielichowski.commons.Money
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody

@Controller
internal class AccountController(private val accountFacade: AccountFacade) {

    @GetMapping("/accounts/{accountId}/balance")
    @ResponseBody
    fun getAccountBalance(@PathVariable accountId: String): Money {
        return accountFacade.getBalanceAs(accountId, Currency.USD)
    }

//    @PostMapping("/accounts")
//    @ResponseBody
//    fun createAccount(): AccountDto {
//        return accountFacade.createAccount(Money(BigDecimal.valueOf(100), Currency.PLN))
//    }

}