package com.zielichowski.account.api

import com.zielichowski.commons.Money
import java.util.*

data class AccountId(val id: UUID)

class AccountInitializationException(message: String) : RuntimeException(message)

data class AccountDto(val accountId: String, val money: Money)

data class CreateAccountRequest(
    val amount: Long,
    val currency: String
)