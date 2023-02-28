package gr.jvoyatz.blase.domain.usecases

import gr.jvoyatz.blase.core.models.TestTest

class GetRandomActivityImpl: GetRandomActivity {

    override operator fun invoke(): TestTest {
        return TestTest()
    }
}