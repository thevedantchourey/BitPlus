package com.app.bitplus

import android.app.Application
import com.app.bitplus.crypto.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BitPlusApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BitPlusApp)
            androidLogger()
            modules(appModule)
        }
    }
}