import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object HttpUtil {

    const val DEBUG = true

    private val executorService: ExecutorService = Executors.newCachedThreadPool()
    private var okHttpClient = OkHttpClient()
    private var mediaTypeJson = "application/json".toMediaTypeOrNull()
    private var mediaTypeX = "application/x-www-form-urlencoded".toMediaTypeOrNull()
    private val gson = Gson()

    fun <T> get(url: String, clazz: Class<T>, callback: (t: T) -> Unit) {
        executorService.submit {
            Request.Builder().url(url).build().also { enqueue(okHttpClient.newCall(it), clazz, callback) }
        }
    }

    fun <T> post(url: String, jsonObject: JsonObject, clazz: Class<T>, callback: (t: T) -> Unit) {
        executorService.submit {
            val requestBody = jsonObject.toString().toRequestBody(mediaTypeJson)
            Request.Builder().url(url).post(requestBody).build()
                .also { enqueue(okHttpClient.newCall(it), clazz, callback) }
        }
    }

    fun <T> postWithHeader(
        url: String,
        content: String,
        map: Map<String, String>,
        clazz: Class<T>,
        callback: (t: T) -> Unit
    ) {
        executorService.submit {
            val requestBody = content.toRequestBody(mediaTypeX)
            Request.Builder().url(url).post(requestBody).apply {
                map.forEach { (k, v) -> addHeader(k, v) }
            }.also { enqueue(okHttpClient.newCall(it.build()), clazz, callback) }
        }
    }


    private fun <T> enqueue(
        call: Call,
        clazz: Class<T>,
        callback: (t: T) -> Unit
    ) {
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                response.body?.string().apply {
                    if (DEBUG) println(this)
                    if (this == null) {
                        System.err.println("response body is empty!!!")
                        return
                    }
                    try {
                        //处理空分数
                        replace("\"credit_num\":\"\"", "\"credit_num\":0.0").also {
                            callback(gson.fromJson(it, clazz))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}