package gr.jvoyatz.blase.core.network.v1.utils

import com.google.common.truth.Truth
import gr.jvoyatz.blase.core.network.v1.*
import gr.jvoyatz.blase.testing.MainDispatcherRule
import gr.jvoyatz.blase.testing.RetrofitMockData
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class NetworkUtilsTest{

    @get:Rule
    val dispatcherRule: MainDispatcherRule = MainDispatcherRule()

    @Test
    fun `safe api call with success body, returns api success`() = runTest(dispatcherRule.testDispatcher){
        //given
        val body = RetrofitMockData.RESPONSE

        //when
        val response = safeApiCall {
            Response.success(body)
        }

        Truth.assertThat(response).isNotNull()
        Truth.assertThat(response.isSuccess()).isTrue()
        Truth.assertThat(response.asSuccess()!!.response).isEqualTo(body)
    }

    @Test
    fun `safe api call with empty(null) body, returns api success empty`() = runTest(dispatcherRule.testDispatcher){
        //given
        val body = null

        //when
        val response = safeApiCall {
            Response.success(body)
        }

        Truth.assertThat(response).isNotNull()
        Truth.assertThat(response.isSuccessEmpty()).isTrue()
    }

    @Test
    fun `safe api call with unit body, returns api success empty`() = runTest(dispatcherRule.testDispatcher){
        //given
        val body = Unit

        //when
        val response = safeApiCall {
            Response.success(body)
        }

        Truth.assertThat(response).isNotNull()
        Truth.assertThat(response.isSuccessEmpty()).isTrue()
    }


    @Test
    fun `safe api call http error bad request, returns http error `() = runTest {
        //given
        val body = RetrofitMockData.ERROR_RESPONSE

        //when
        val errorResponse = safeApiCall {
            Response.error(400, body)
        }

        //then
        Truth.assertThat(errorResponse).isInstanceOf(ApiResponse.ApiError.HttpError::class.java)
        Truth.assertThat(errorResponse.asHttpError()!!.code).isEqualTo(400)
    }


    @Test
    fun `safe api call network error, returns api network error `() = runTest {
        //given
        val body = null

        //when
        val errorResponse = safeApiCall {
            throw IOException("io exception ")
            Response.error(500, body)
        }

        //then
        Truth.assertThat(errorResponse).isInstanceOf(ApiResponse.ApiError.NetworkError::class.java)
        Truth.assertThat(errorResponse.asNetworkError()!!.io).isInstanceOf(IOException::class.java)
    }

    @Test
    fun `safe api call unexpected error, returns api unknown error `() = runTest {
        //given
        val body = RetrofitMockData.RESPONSE

        //when
        val errorResponse = safeApiCall {
            throw IllegalStateException("test state exception")
            Response.success(body)
        }

        //then
        Truth.assertThat(errorResponse).isInstanceOf(ApiResponse.ApiError.UnknownError::class.java)
        Truth.assertThat(errorResponse.asUnknownError()!!.error).isInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun `safe raw api call with success body, returns api success`() = runTest(dispatcherRule.testDispatcher){
        //given
        val body = RetrofitMockData.RESPONSE

        //when
        val response = safeRawApiCall {
            body
        }

        Truth.assertThat(response).isNotNull()
        Truth.assertThat(response.isSuccess()).isTrue()
        Truth.assertThat(response.asSuccess()!!.response).isEqualTo(body)
    }

    @Test
    fun `safe raw api call with empty(null) body, returns api success empty`() = runTest(dispatcherRule.testDispatcher){
        //given
        val body = null

        //when
        val response = safeRawApiCall {
            body
        }

        println(response)

        Truth.assertThat(response).isNotNull()
        Truth.assertThat(response.isSuccessEmpty()).isTrue()
    }

    @Test
    fun `safe raw api call with unit body, returns api success empty`() = runTest(dispatcherRule.testDispatcher){
        //given
        val body = Unit

        //when
        val response = safeRawApiCall {
            body
        }

        Truth.assertThat(response).isNotNull()
        Truth.assertThat(response.isSuccessEmpty()).isTrue()
    }


    @Test
    fun `safe raw api call http error bad request, returns http error `() = runTest {
        //given
        val body = RetrofitMockData.ERROR_RESPONSE
        val error = Response.error<Any>(400, body)

        //when
        val errorResponse = safeRawApiCall {
            throw HttpException(error)
        }

        //then
        Truth.assertThat(errorResponse).isInstanceOf(ApiResponse.ApiError.HttpError::class.java)
        Truth.assertThat(errorResponse.asHttpError()!!.code).isEqualTo(400)
    }


    @Test
    fun `safe raw api call network error, returns api network error `() = runTest {
        //given
        val body = null

        //when
        val errorResponse = safeRawApiCall {
            throw IOException("io exception ")
        }

        //then
        Truth.assertThat(errorResponse).isInstanceOf(ApiResponse.ApiError.NetworkError::class.java)
        Truth.assertThat(errorResponse.asNetworkError()!!.io).isInstanceOf(IOException::class.java)
    }

    @Test
    fun `safe raw api call unexpected error, returns api unknown error `() = runTest {
        //given
        val body = RetrofitMockData.RESPONSE

        //when
        val errorResponse = safeRawApiCall {
            throw IllegalStateException("test exception")
        }

        //then
        Truth.assertThat(errorResponse).isInstanceOf(ApiResponse.ApiError.UnknownError::class.java)
        Truth.assertThat(errorResponse.asUnknownError()!!.error).isInstanceOf(IllegalStateException::class.java)
    }
}