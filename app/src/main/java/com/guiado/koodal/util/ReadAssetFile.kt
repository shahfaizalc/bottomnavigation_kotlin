package com.guiado.koodal.util


import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

internal class ReadAssetFile {

    fun readAssetFile(path: String, context: Context): String {

        var contents = ""
        val assetManager = context.assets

        try {
            assetManager.open(path).use { inputString -> BufferedReader(InputStreamReader(inputString)).use { reader -> contents = readFileContent(reader) } }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return contents
    }

    @Throws(IOException::class)
    private fun readFileContent(reader: BufferedReader): String {
        val contents1 = StringBuilder(reader.readLine())
        var line: String? = reader.readLine()

        while (line != null) {
            contents1.append('\n').append(line)
            line = reader.readLine()
        }
        return contents1.toString()
    }
}
