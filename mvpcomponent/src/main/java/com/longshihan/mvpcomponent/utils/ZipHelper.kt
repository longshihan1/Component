package com.longshihan.mvpcomponent.utils

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.Closeable
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.ArrayList
import java.util.zip.DataFormatException
import java.util.zip.Deflater
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import java.util.zip.Inflater

/**
 * Created by jess on 16/5/10.
 */
object ZipHelper {


    /**
     * zlib decompress 2 String
     *
     * @param bytesToDecompress
     * @param charsetName
     * @return
     */
    @JvmOverloads
    fun decompressToStringForZlib(bytesToDecompress: ByteArray, charsetName: String = "UTF-8"): String? {
        val bytesDecompressed = decompressForZlib(
                bytesToDecompress
        )

        var returnValue: String? = null

        try {
            returnValue = String(bytesDecompressed!!,
                    0, bytesDecompressed.size, Charset.defaultCharset()
            )
        } catch (uee: UnsupportedEncodingException) {
            uee.printStackTrace()
        }

        return returnValue

    }


    /**
     * zlib decompress 2 byte
     *
     * @param bytesToDecompress
     * @return
     */
    fun decompressForZlib(bytesToDecompress: ByteArray): ByteArray? {
        var returnValues: ByteArray? = null

        val inflater = Inflater()

        val numberOfBytesToDecompress = bytesToDecompress.size

        inflater.setInput(
                bytesToDecompress,
                0,
                numberOfBytesToDecompress
        )

        var numberOfBytesDecompressedSoFar = 0
        val bytesDecompressedSoFar = ArrayList<Byte>()

        try {
            while (inflater.needsInput() == false) {
                val bytesDecompressedBuffer = ByteArray(numberOfBytesToDecompress)

                val numberOfBytesDecompressedThisTime = inflater.inflate(
                        bytesDecompressedBuffer
                )

                numberOfBytesDecompressedSoFar += numberOfBytesDecompressedThisTime

                for (b in 0 until numberOfBytesDecompressedThisTime) {
                    bytesDecompressedSoFar.add(bytesDecompressedBuffer[b])
                }
            }

            returnValues = ByteArray(bytesDecompressedSoFar.size)
            for (b in returnValues.indices) {
                returnValues[b] = bytesDecompressedSoFar[b]
            }

        } catch (dfe: DataFormatException) {
            dfe.printStackTrace()
        }

        inflater.end()

        return returnValues
    }

    /**
     * zlib compress 2 byte
     *
     * @param bytesToCompress
     * @return
     */
    fun compressForZlib(bytesToCompress: ByteArray): ByteArray {
        val deflater = Deflater()
        deflater.setInput(bytesToCompress)
        deflater.finish()

        val bytesCompressed = ByteArray(java.lang.Short.MAX_VALUE.toInt())

        val numberOfBytesAfterCompression = deflater.deflate(bytesCompressed)

        val returnValues = ByteArray(numberOfBytesAfterCompression)

        System.arraycopy(
                bytesCompressed,
                0,
                returnValues,
                0,
                numberOfBytesAfterCompression
        )

        return returnValues
    }

    /**
     * zlib compress 2 byte
     *
     * @param stringToCompress
     * @return
     */
    fun compressForZlib(stringToCompress: String): ByteArray? {
        var returnValues: ByteArray? = null

        try {

            returnValues = compressForZlib(
                    stringToCompress.toByteArray(charset("UTF-8"))
            )
        } catch (uee: UnsupportedEncodingException) {
            uee.printStackTrace()
        }

        return returnValues
    }

    /**
     * gzip compress 2 byte
     *
     * @param string
     * @return
     * @throws IOException
     */
    fun compressForGzip(string: String): ByteArray? {
        var os: ByteArrayOutputStream? = null
        var gos: GZIPOutputStream? = null
        try {
            os = ByteArrayOutputStream(string.length)
            gos = GZIPOutputStream(os)
            gos.write(string.toByteArray(charset("UTF-8")))
            return os.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            closeQuietly(gos)
            closeQuietly(os)
        }
        return null
    }

    /**
     * gzip decompress 2 string
     *
     * @param compressed
     * @param charsetName
     * @return
     */
    @JvmOverloads
    fun decompressForGzip(compressed: ByteArray, charsetName: String = "UTF-8"): String? {
        val BUFFER_SIZE = compressed.size
        var gis: GZIPInputStream? = null
        var `is`: ByteArrayInputStream? = null
        try {
            `is` = ByteArrayInputStream(compressed)
            gis = GZIPInputStream(`is`, BUFFER_SIZE)
            val string = StringBuilder()
            val data = ByteArray(BUFFER_SIZE)
            var bytesRead: Int
//            while ((bytesRead = gis.read(data)) != -1) {
//                string.append(String(data, 0, bytesRead, charsetName))
//            }
            return string.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            closeQuietly(gis)
            closeQuietly(`is`)
        }
        return null
    }

    fun closeQuietly(closeable: Closeable?) {
        if (closeable != null) {
            try {
                closeable.close()
            } catch (rethrown: RuntimeException) {
                throw rethrown
            } catch (ignored: Exception) {
            }

        }
    }
}
/**
 * zlib decompress 2 String
 *
 * @param bytesToDecompress
 * @return
 */
/**
 * gzip decompress 2 string
 *
 * @param compressed
 * @return
 * @throws IOException
 */
