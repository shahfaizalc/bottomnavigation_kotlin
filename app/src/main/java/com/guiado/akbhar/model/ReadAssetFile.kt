package com.guiado.akbhar.model

import android.content.res.AssetManager
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class ReadAssetFile {

    fun readFile(assetManager: AssetManager, path: String): String {
        var contents = ""
        var inputString: InputStream? = null
        var reader: BufferedReader? = null
        try {
            inputString = assetManager.open(path)
            reader = BufferedReader(InputStreamReader(inputString))
            contents = readFileContent(contents, reader)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (inputString != null) {
                try {
                    inputString.close()
                } catch (ignored: IOException) {
                }

            }
            if (reader != null) {
                try {
                    reader.close()
                } catch (ignored: IOException) {
                }

            }
        }
        return contents
    }

    private fun readFileContent(contents: String, reader: BufferedReader): String {
        var contents1 = contents
        contents1 = reader.readLine()
        var line: String? = reader.readLine()

        while (line != null) {
            contents1 += '\n' + line
            line = reader.readLine()
        }
        return contents1
    }
}