package io.kotless.dsl.ktor.app

import io.kotless.dsl.model.HttpRequest
import io.ktor.http.Headers
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.RequestConnectionPoint
import io.ktor.server.application.ApplicationCall
import io.ktor.server.engine.BaseApplicationRequest
import io.ktor.server.request.ApplicationReceivePipeline
import io.ktor.server.request.RequestCookies
import io.ktor.utils.io.ByteReadChannel

/**
 * Ktor Request used by Kotless. It will be created from APIGateway request.
 */
class KotlessRequest(val query: HttpRequest, call: ApplicationCall) : BaseApplicationRequest(call) {
    override val pipeline = ApplicationReceivePipeline().apply {
        merge(call.application.receivePipeline)
    }

    override val cookies: RequestCookies = RequestCookies(this)

    override val headers: Headers = Headers.build {
        query.headers.forEach { appendAll(it.key, listOf(it.value)) }
    }

    override val local: RequestConnectionPoint = object : RequestConnectionPoint {
        @Deprecated("Use localHost or serverHost instead")
        override val host: String = query.context.domain
        override val localAddress: String
            get() = TODO("Not yet implemented")
        override val localHost: String
            get() = TODO("Not yet implemented")
        override val localPort: Int
            get() = TODO("Not yet implemented")
        override val method: HttpMethod = HttpMethod.parse(query.method.name)

        //Port is not applicable in case of Serverless execution
        @Deprecated("Use localPort or serverPort instead")
        override val port: Int = -1
        override val remoteAddress: String
            get() = TODO("Not yet implemented")
        override val remoteHost: String = query.context.sourceIp
        override val remotePort: Int
            get() = TODO("Not yet implemented")
        override val scheme: String = query.context.protocol
        override val serverHost: String
            get() = TODO("Not yet implemented")
        override val serverPort: Int
            get() = TODO("Not yet implemented")
        override val uri: String = query.path
        override val version: String = query.context.protocol
    }

    override val queryParameters: Parameters = Parameters.build {
        query.params.forEach { append(it.key, it.value) }
    }
    override val rawQueryParameters: Parameters = queryParameters

    override fun receiveChannel() = ByteReadChannel(query.body?.bytes ?: ByteArray(0))
}
