package com.zielichowski.account.it

import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.zielichowski.account.api.AccountDto
import com.zielichowski.account.domain.AccountFacade
import com.zielichowski.commons.Currency
import com.zielichowski.commons.Money
import groovy.json.JsonSlurper
import org.junit.ClassRule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Shared
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig

@SpringBootTest
@AutoConfigureMockMvc
class AccountTestIT extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private AccountFacade accountFacade

    private static String API_PREFIX = "/v1/api"

    @ClassRule
    @Shared
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig()
            .bindAddress("localhost")
            .port(9000))

    @Shared
    def jsonSlurper = new JsonSlurper()

    def "Should create account with initial balance in pln"() {

        given: "Account create request"
        def request = getClass().getResource("create_account_request.json").text

        and: "Expected response"
        def expectedResponse = new AccountDto(UUID.randomUUID().toString(), new Money(100, Currency.PLN))

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders
                .post(API_PREFIX + "/accounts/")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .response

        then:
        response.status == 201
    }

    def "Should get balance in USD"() {
        given: "account id"
        def accountId = accountFacade.createAccount(new Money(100, Currency.PLN)).accountId

        stubFor(get(urlEqualTo("/api/exchangerates/rates/A/USD"))
                .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody(getClass().getResource("nbp_rates_response.json").text)
                ))

        and: "Expected response body"
        def expectedResponseBody = jsonSlurper.parse(getClass().getResource("account_balance_response.json"))

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders
                .get(API_PREFIX + "/accounts/${accountId}/balance")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .response

        then:
        def responseBody = jsonSlurper.parseText(response.contentAsString)

        responseBody == expectedResponseBody
    }

    def "Should return bad request if account not exists"() {
        given: "wrong account id"
        def accountId = "12345"

        and: "Stub external service"
        stubFor(get(urlEqualTo("/api/exchangerates/rates/A/USD"))
                .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody(getClass().getResource("nbp_rates_response.json").text)
                ))

        and: "Expected response body"
        def expectedResponseBody = jsonSlurper.parse(getClass().getResource("account_balance_not_found_response.json"))

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders
                .get(API_PREFIX + "/accounts/${accountId}/balance")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .response

        then: "response status is Bad request"
        response.status == 404

        and: "body contains error message and status"
        def responseBody = jsonSlurper.parseText(response.contentAsString)

        responseBody == expectedResponseBody
    }

    def "Should return nice exception message in case of client error"() {
        given: "account id"
        def accountId = accountFacade.createAccount(new Money(100, Currency.PLN)).accountId

        stubFor(get(urlEqualTo("/api/exchangerates/rates/A/USD"))
                .willReturn(
                        aResponse()
                                .withStatus(400)
                                .withBody("400 BadRequest - Bad Request - Nieprawidlowa wartosc parametru:")
                ))

        and: "Expected response body"
        def expectedResponseBody = jsonSlurper.parse(getClass().getResource("account_balance_provider_error.json"))

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders
                .get(API_PREFIX + "/accounts/${accountId}/balance")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .response

        then:
        def responseBody = jsonSlurper.parseText(response.contentAsString)

        responseBody == expectedResponseBody
    }

    def "Should return bad request in case of error creating account"() {

        given: "Account create request"
        def request = getClass().getResource("create_account_request_error.json").text

        and: "Expected response"
        def expectedResponse = jsonSlurper.parse(getClass().getResource("account_create_error_response.json"))

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders
                .post(API_PREFIX + "/accounts/")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .response

        then:
        response.status == 400

        and:
        def responseBody = jsonSlurper.parseText(response.contentAsString)
        responseBody == expectedResponse

    }

}
