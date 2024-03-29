package gr.jvoyatz.blase.testing

import com.google.gson.annotations.JsonAdapter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

object RetrofitMockData {
    const val RESPONSE = "test response"
    val ERROR_RESPONSE = "{\"error\": [\"errorError\"]}"
    val ERROR_RESPONSE_BODY = ERROR_RESPONSE.toResponseBody("application/json".toMediaTypeOrNull())
}