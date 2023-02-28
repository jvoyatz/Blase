package gr.jvoyatz.blase.common

import gr.jvoyatz.blase.core.models.TestTest
import gr.jvoyatz.blase.core.usecasesTestTe.GetRandomActivity

class AnotherClass {
    init {
        val a: GetRandomActivity?=null

        val invoke: TestTest = a!!.invoke()
        invoke.x
    }
}