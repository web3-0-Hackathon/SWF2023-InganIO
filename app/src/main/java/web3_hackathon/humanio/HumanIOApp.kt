package web3_hackathon.humanio

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HumanIOApp: Application() {
    companion object {
        lateinit var instance: HumanIOApp

        fun getInstance(): Application {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }
}