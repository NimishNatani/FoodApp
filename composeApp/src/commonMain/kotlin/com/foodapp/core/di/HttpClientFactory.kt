package com.foodapp.core.di

import com.foodapp.foodapp.storage.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientFactory(private val tokenStorage: TokenStorage) {

    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(HttpTimeout) {
                socketTimeoutMillis = 200_000L
                requestTimeoutMillis = 200_000L
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.ALL
            }
            install(Auth) {
//                bearer {
//                    loadTokens {
//                        BearerTokens(
//                            accessToken = tokenStorage.getToken().toString(),
//                            refreshToken = ""
//                        )
//                    }
//                    sendWithoutRequest { request ->
//                        request.url.host == "http://localhost:8080/api/login/restaurant" ||
//                                request.url.host == "http://localhost:8080/api/register/restaurant" ||
//                                request.url.host == "http://localhost:8080/api/login/user" ||
//                                request.url.host == "http://localhost:8080/api/register/user"
//
//                    }
//                }
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}