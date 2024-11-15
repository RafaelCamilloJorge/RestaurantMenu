package com.unaerp.restaurantmenu.core.injection_dependencie

import android.app.Application
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(DependencyInitializer().appModule)
        }
    }
}
