package gr.jvoyatz.blase

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import gr.jvoyatz.blase.logging.LogEvent

@HiltAndroidApp
class BlaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        LogEvent.init()
        LogEvent.d("app created")
    }
}