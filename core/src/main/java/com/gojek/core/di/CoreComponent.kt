package com.gojek.core.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface CoreComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }

    fun retrofit(): Retrofit
}

object CoreComponentProvider {
    private var component: CoreComponent? = null

    @Synchronized
    fun get(): CoreComponent {
        if (component == null) {
            throw IllegalStateException("CoreComponent is not initialized")
        }
        return component!!
    }

    @Synchronized
    fun init(context: Context): CoreComponent {
        if (component == null) {
            component = DaggerCoreComponent.factory()
                .create(context.applicationContext)
        }
        return component!!
    }
}