package com.example.daggermvvm.network.di

import com.example.daggermvvm.AppModule
import com.example.daggermvvm.network.repository.RetrofitRepository
import com.example.daggermvvm.network.view.RetroFragment
import com.example.daggermvvm.network.viewmodel.RetroViewModel
import com.example.daggermvvm.network.viewmodel.RetroViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (modules = [AppModule::class,APIModule::class])
interface APIComponent {
    fun inject(retrofitRepository: RetrofitRepository)
    fun inject(retroViewModel: RetroViewModel)
    fun inject(retroFragment: RetroFragment)
    fun inject(retroViewModelFactory: RetroViewModelFactory)
}