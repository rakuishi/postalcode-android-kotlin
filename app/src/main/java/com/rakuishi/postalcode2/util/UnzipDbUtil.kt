package com.rakuishi.postalcode2.util

import android.content.Context
import android.content.res.AssetManager
import timber.log.Timber
import java.io.FileOutputStream
import java.util.zip.ZipInputStream

class UnzipDbUtil {

    companion object {

        fun unzipIfNecessary(context: Context, zipName: String, toName: String) {
            if (context.applicationContext.getDatabasePath(toName).exists()) {
                Timber.d("Database($toName) is already exist.")
            } else {
                copyAndUnzipDbFromAsset(context, zipName, toName)
            }
        }

        private fun copyAndUnzipDbFromAsset(context: Context, zipName: String, toName: String) {
            val assetManager = context.applicationContext.assets
            val inputStream = assetManager.open(zipName, AssetManager.ACCESS_STREAMING)
            val zipInputStream = ZipInputStream(inputStream)
            val zipEntry = zipInputStream.nextEntry
            val toPath = context.applicationContext.getDatabasePath(toName)

            if (!toPath.parentFile.exists()) {
                toPath.parentFile.mkdir()
            }

            if (zipEntry != null) {
                val fileOutputStream = FileOutputStream(toPath, false)
                val buffer = ByteArray(1024)
                var size: Int

                do {
                    size = zipInputStream.read(buffer, 0, buffer.size)
                    if (size < 0) break
                    fileOutputStream.write(buffer, 0, size)
                } while (true)

                fileOutputStream.close()
                zipInputStream.closeEntry()
            }

            zipInputStream.close()

            Timber.d("Database($toName) is unzipped from assets($zipName).")
        }
    }
}