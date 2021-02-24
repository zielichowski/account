package com.zielichowski.exchange.infrastructure.provider

import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.DefaultResponseErrorHandler
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors


internal class NbpResponseErrorHandler : DefaultResponseErrorHandler() {
    override fun handleError(response: ClientHttpResponse) {
        if (response.statusCode.is4xxClientError || response.statusCode.is5xxServerError) {
            BufferedReader(InputStreamReader(response.body)).use { reader ->
                val httpBodyResponse = reader.lines().collect(Collectors.joining(""))
                throw MoneyExchangeException(response.statusCode, httpBodyResponse)
            }
        }
    }
}

class MoneyExchangeException(val statusCode: HttpStatus, val error: String) : RuntimeException()