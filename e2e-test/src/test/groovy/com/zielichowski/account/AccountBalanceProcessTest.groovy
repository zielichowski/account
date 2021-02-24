package com.zielichowski.account

import groovy.json.JsonSlurper
import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient
import spock.lang.Shared
import spock.lang.Specification

class AccountBalanceProcessTest extends Specification {
    @Shared
    def port = 8080
    @Shared
    def restClient = new RESTClient()
    @Shared
    def jsonSlurper = new JsonSlurper()


    def "Should read newly created account balance in usd"() {
        given: "request to create account"
        def createAccountRequest = jsonSlurper.parseText(getClass().getResource("create_account_request.json").text)

        when: "account is crated"
        def accountResponse = restClient.post(uri: "http://localhost:${port}/v1/api/accounts", body: createAccountRequest, headers: ["Content-Type": "application/json"], requestContentType: ContentType.JSON)

        then: "response status is created"
        accountResponse.status == 201

        and: "get account Id"
        def accountId = accountResponse.data["accountId"]

        and: "get balance in usd"
        def response = restClient.get(uri: "http://localhost:${port}/v1/api/accounts/${accountId}/balance", contentType: ContentType.JSON)

        then: "response is ok"
        response.status == 200

        and: "balance is in usd"
        response.data["currencyCode"] == "USD"
    }

}
