package com.example.daggermvvm.network.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.daggermvvm.network.model.PostInfo
import com.example.daggermvvm.network.repository.RetrofitRepository

class RetroViewModel(retrofitRepository: RetrofitRepository): ViewModel() {
    lateinit var retrofitRepository:RetrofitRepository
    var postInfoLiveData: LiveData<List<PostInfo>> = MutableLiveData()

    init {
        this.retrofitRepository  = retrofitRepository
        fetchPostInfoFromRepository()
    }

    fun fetchPostInfoFromRepository(){
        postInfoLiveData =  retrofitRepository.fetchPostInfoList()
    }
}