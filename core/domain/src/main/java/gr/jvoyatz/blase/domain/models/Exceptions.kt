package gr.jvoyatz.blase.domain.models

class BoredException: RuntimeException{
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}
