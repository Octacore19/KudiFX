package com.octacoresoftwares.kudifx

import com.octacoresoftwares.kudifx.remote.NetworkApi
import com.octacoresoftwares.kudifx.remote.Service
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class ServiceTest: BaseTest() {

    private var mockWebServer = MockWebServer()
    private lateinit var service: NetworkApi

    @Before
    fun setUp() {
        mockWebServer.start()
        service = Service.createService(mockWebServer.url("/"))
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `read symbols json file`() {
        val symbols = getJson("symbols.json")
        assert(symbols!!.isNotEmpty())
        reader?.close()
    }
}