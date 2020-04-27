package com.example.daggermvvm.network.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.daggermvvm.R

class NetworkActivity: AppCompatActivity {
    constructor() : super()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.network_layout)
        replaceFragment()
    }
    fun replaceFragment(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_retro_room,RetroFragment())
            .commit()
    }
}