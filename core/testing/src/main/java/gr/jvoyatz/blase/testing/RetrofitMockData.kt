package gr.jvoyatz.blase.testing

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

object RetrofitMockData {
    const val RESPONSE = "test response"
    val ERROR_RESPONSE =
        "{\"testError\": [\"errorError\"]}".toResponseBody("application/json".toMediaTypeOrNull())
}