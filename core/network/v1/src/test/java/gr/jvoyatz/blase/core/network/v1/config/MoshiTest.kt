package gr.jvoyatz.blase.core.network.v1.config

import com.google.common.truth.Truth
import com.squareup.moshi.Json
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


data class Activity(@Json(name = "activity") val activity: String)
data class ActivityInvalid(@Json(name = "activityyyy") val activity: String)
data class GenericErrorResponse(@Json(name = "error") val error: String)
data class GenericErrorResponseInvalid(@Json(name = "erro1rrr") val error: String)


interface BoredApiServiceTest {
    @GET("/activity")
    suspend fun getActivity(): ApiResponse<Activity, GenericErrorResponse>

    @GET("/activity")
    suspend fun getActivityInvalid(): ApiResponse<ActivityInvalid, GenericErrorResponseInvalid>
}


internal class TestBoredApplication(
    private val apiService: BoredApiServiceTest
) {
    fun getActivity(): ApiResponse<Activity, GenericErrorResponse> = runBlocking {
        apiService.getActivity()
    }

    fun getActivityInvalid(): ApiResponse<ActivityInvalid, GenericErrorResponseInvalid> = runBlocking {
        apiService.getActivityInvalid()
    }
}


class MoshiTest{
    private lateinit var server: MockWebServer

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    lateinit var retrofit: Retrofit

    lateinit var service: BoredApiServiceTest

    @Before
    fun setup() {
        val client = OkHttpClient.Builder()/*.callTimeout(Duration.ofMillis(100))*/.build()
        server = MockWebServer()
        server.start()

        retrofit = Retrofit.Builder()
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(server.url("/"))
            .client(client)
            .build()

        service = retrofit.create(BoredApiServiceTest::class.java)
    }

    @After
    fun teardown(){
        server.close()
    }

    @Test
    fun `parsing successful response successfully`(){
        val testApp = TestBoredApplication(service)

        server.enqueue(MockResponse().apply {
            setBody(javaClass.classLoader!!.getResourceAsStream("activity.json").bufferedReader().use { it.readText() })
            setResponseCode(200)
        })

        val response = testApp.getActivity()
        Truth.assertThat(response).isInstanceOf(ApiSuccess::class.java)
        Truth.assertThat(response()!!.activity).isEqualTo("Learn Express.js")
    }


    @Test
    fun `parsing error response successfully`(){
        val testApp = TestBoredApplication(service)

        server.enqueue(MockResponse().apply {
            setBody(javaClass.classLoader!!.getResourceAsStream("error_response.json").bufferedReader().use { it.readText() })
            setResponseCode(404)
        })

        val response = testApp.getActivity()
        Truth.assertThat(response).isInstanceOf(HttpError::class.java)
        Truth.assertThat(response()).isNull()
        val httpError = response as HttpError
        Truth.assertThat(httpError.errorBody?.error).isEqualTo("Endpoint not found")
        Truth.assertThat((response as HttpError).code).isEqualTo(404)
    }

    @Test
    fun `error network response when successful response  parsing fails`(){
        val testApp = TestBoredApplication(service)

        server.enqueue(MockResponse().apply {
            setBody(javaClass.classLoader!!.getResourceAsStream("activity.json").bufferedReader().use { it.readText() })
            setResponseCode(200)
        })

        val response = testApp.getActivityInvalid()
        Truth.assertThat(response).isInstanceOf(UnknownError::class.java)
        val unknownError = response as UnknownError
        Truth.assertThat(unknownError.error).isInstanceOf(JsonDataException::class.java)
    }

    @Test
    fun `unsuccessful response with parsing exception`(){
        val testApp = TestBoredApplication(service)

        server.enqueue(MockResponse().apply {
            setBody("""{ "random" : "test" } """)
            setResponseCode(422)
        })

        val response = testApp.getActivityInvalid()
        Truth.assertThat(response).isInstanceOf(UnknownError::class.java)
        val unknownError = response as UnknownError
        Truth.assertThat(unknownError.error).isInstanceOf(JsonDataException::class.java)
    }

    @Test
    fun `fail when parsing error response`(){
        val testApp = TestBoredApplication(service)

        server.enqueue(MockResponse().apply {
            setBody(javaClass.classLoader!!.getResourceAsStream("error_response.json").bufferedReader().use { it.readText() })
            setResponseCode(200)
        })

        val response = testApp.getActivityInvalid()
        Truth.assertThat(response).isInstanceOf(UnknownError::class.java)
        val unknownError = response as UnknownError
        Truth.assertThat(unknownError.error).isInstanceOf(JsonDataException::class.java)
    }
}