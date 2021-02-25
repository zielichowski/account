package com.zielichowski.exchange.infrastructure.provider

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.client.RestTemplate

internal class NbpExchangeClient(private val restTemplate: RestTemplate, private val urlTemplate: String) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(NbpExchangeClient::class.java)
    }

    fun getRate(currency: String): NbpRateResponse {
        logger.info("Calling nbp exchange, url: $urlTemplate$currency")
        val responseEntity = restTemplate.getForEntity(urlTemplate + currency, NbpRateResponse::class.java)
        logger.info("Nbp exchange response status ${responseEntity.statusCode}")
        return responseEntity.body!!
    }
}