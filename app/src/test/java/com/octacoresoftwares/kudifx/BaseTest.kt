package com.octacoresoftwares.kudifx

import java.io.InputStreamReader

open class BaseTest {

    var reader: InputStreamReader? = null

    fun getJson(path: String): String? {
        reader = InputStreamReader(this.javaClass.getResourceAsStream(path))
        return reader!!.readText()
    }
}