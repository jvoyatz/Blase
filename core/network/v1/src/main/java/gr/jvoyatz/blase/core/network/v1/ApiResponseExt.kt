package gr.jvoyatz.blase.core.network.v1



fun <T : Any> ApiResponse<T>.asSuccess():ApiResponse.ApiSuccess<T>?{
    if(this is ApiResponse.ApiSuccess){
        return this
    }
    return null
}

fun <T : Any> ApiResponse<T>.asError():ApiResponse.ApiError<T>?{
    if(this is ApiResponse.ApiError){
        return this
    }
    return null
}

fun <T : Any> ApiResponse<T>.asHttpError():ApiResponse.ApiError.HttpError<T>?{
    if(this is ApiResponse.ApiError.HttpError){
        return this
    }
    return null
}

fun <T : Any> ApiResponse<T>.asNetworkError():ApiResponse.ApiError.NetworkError<T>?{
    if(this is ApiResponse.ApiError.NetworkError){
        return this
    }
    return null
}

fun <T : Any> ApiResponse<T>.asUnknownError():ApiResponse.ApiError.UnknownError<T>?{
    if(this is ApiResponse.ApiError.UnknownError){
        return this
    }
    return null
}

fun <T: Any> ApiResponse<T>.isSuccess() = this is ApiResponse.ApiSuccess
fun <T: Any> ApiResponse<T>.isSuccessEmpty() = this is ApiResponse.ApiSuccessEmpty
fun <T: Any> ApiResponse<T>.isError() = this is ApiResponse.ApiError
