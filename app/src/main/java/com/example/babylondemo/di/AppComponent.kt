package com.example.babylondemo.di

import android.app.Application
import com.example.babylondemo.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(modules = [AndroidInjectionModule::class, BuildersModule::class, ApiModule::class, RepositoryModule::class])
@Singleton
interface AppComponent {

    fun inject(app: App)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        @BindsInstance fun application(application: Application): Builder
    }
}