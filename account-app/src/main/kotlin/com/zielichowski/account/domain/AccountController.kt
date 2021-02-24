package com.zielichowski.account.domain

import com.zielichowski.account.api.AccountDto
import com.zielichowski.account.api.CreateAccountRequest
import com.zielichowski.commons.Currency
import com.zielichowski.commons.Money
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping(value = ["/v1/api"])
internal class AccountController(private val accountFacade: AccountFacade) {

    @GetMapping("/accounts/{accountId}/balance")
    @ResponseBody
    fun getAccountBalance(@PathVariable accountId: String): Money {
        return accountFacade.getBalanceAs(accountId, Currency.USD)
    }

    @PostMapping("/accounts")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    fun createAccount(@RequestBody createAccountRequest: CreateAccountRequest): AccountDto {
        return accountFacade.createAccount(
            Money(
                BigDecimal.valueOf(createAccountRequest.amount),
                Currency.of(createAccountRequest.currency)
            )
        )
    }

}