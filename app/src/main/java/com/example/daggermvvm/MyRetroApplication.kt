package com.example.daggermvvm

import android.app.Application
import android.content.Context
import com.example.daggermvvm.network.di.APIComponent
import com.example.daggermvvm.network.di.APIModule
import com.example.daggermvvm.network.di.DaggerAPIComponent
import com.example.daggermvvm.network.repository.APIURL

class MyRetroApplication : Application() {


    companion object {
        var ctx: Context? = null
        lateinit var apiComponent: APIComponent
    }
    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
        apiComponent = initDaggerComponent()

    }

    fun getMyComponent(): APIComponent {
        return apiComponent
    }

    fun initDaggerComponent():APIComponent{
        apiComponent = DaggerAPIComponent
            .builder()
            .aPIModule(APIModule(APIURL.BASE_URL))
            .build()
        return  apiComponent

    }
}