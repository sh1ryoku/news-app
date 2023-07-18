package ru.shiryoku.news.app

import android.app.Application
import org.koin.core.context.startKoin
import ru.shiryoku.news.di.dataModule

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                modules = dataModule,
            )
        }
    }
}