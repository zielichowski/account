package com.zielichowski.account.infrastructure.persistence

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class AccountEntity(
        @Id var accountId: String,
        var amount: String,
        var currency: String
)