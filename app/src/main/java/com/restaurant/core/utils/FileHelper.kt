package com.restaurant.core.utils

import android.app.Application
import java.io.BufferedReader
import java.io.InputStreamReader

class FileHelper(private val application: Application) {

    fun load(name : String) : String {
        val reader = BufferedReader(InputStreamReader(application.assets.open(name)))
        var line: String?
        val builder = StringBuilder()
        while (reader.readLine().also { line = it } != null) {
            builder.append(line)
        }
        reader.close()
        return builder.toString()
    }
}