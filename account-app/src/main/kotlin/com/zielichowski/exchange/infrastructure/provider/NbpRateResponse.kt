package com.zielichowski.exchange.infrastructure.provider

internal data class Rate(
        val effectiveDate: String,
        val mid: Double,
        val no: String
)

internal data class NbpRateResponse(
        val code: String,
        val currency: String,
        val rates: List<Rate>,
        val table: String
)