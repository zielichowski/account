package com.zielichowski.exchange.infrastructure.provider

import org.springframework.web.client.RestTemplate

internal class NbpExchangeClient(private val restTemplate: RestTemplate, private val urlTemplate: String) {

    fun getRate(currency: String): NbpRateResponse {
        return restTemplate.getForEntity(urlTemplate + currency, NbpRateResponse::class.java)
                .body!!
    }
}