package king.basic.log

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.file.Paths
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    fun generateFileWithRepeatedLines(filePath: String, line: String, fileSize: Long) {
        val file = File(filePath)
        val index = AtomicInteger()

        try {
            BufferedWriter(FileWriter(file)).use { writer ->
                var currentSize = 0L
                while (currentSize < fileSize) {
                    val line = "${line}${index.getAndIncrement()}"
                    writer.write(line)
                    writer.newLine()
                    // 每次写入一行后，更新当前文件大小
                    currentSize += line.toByteArray().size + System.lineSeparator()
                        .toByteArray().size
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        val currentDirectory = System.getProperty("user.dir")
        val srcF = File(currentDirectory, "src.txt")

        measureTimeMillis {
            generateFileWithRepeatedLines(
                srcF.absolutePath,
                "This is a repeated line.",
                500 * 1024 * 1024 // 500M
            )
        }.printfTime("genSrc")

        val outF = File(srcF.parent, "des.txt")

        val fis = srcF.inputStream()
        val fos = outF.outputStream()

        val inChannel = fis.channel
        val outChannel = fos.channel

        measureTimeMillis {
            copyFile(srcF.absolutePath, outF.absolutePath)
        }.printfTime("copy")
    }

    @Throws(IOException::class)
    fun copyFile(srcPath: String, destPath: String) {
        FileInputStream(File(srcPath)).channel.use { inChannel ->
            FileOutputStream(File(destPath)).channel.use { outChannel ->
                val buffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE)
                while (inChannel.read(buffer) != -1) {
                    buffer.flip()
                    outChannel.write(buffer)
                    buffer.clear()
                }
            }
        }
    }

    fun <T> T.printfTime(plc: String) {
        println("${plc} time cost : ${this}")
    }
}