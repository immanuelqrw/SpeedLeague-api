package com.immanuelqrw.speedleague.api

import io.micronaut.context.ApplicationContext
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import org.junit.jupiter.api.Assertions.assertEquals


fun main() {

    val embeddedServer : EmbeddedServer = ApplicationContext.run(EmbeddedServer::class.java)
    val client : HttpClient = HttpClient.create(embeddedServer.url)

    val rsp : String = client.toBlocking().retrieve("/hello")
    assertEquals(rsp, "Hello World")

    client.close()
    embeddedServer.close()

}
