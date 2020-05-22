package com.example.levanmidterm1

import android.app.Application
import com.example.levanmidterm1.api.RetrofitClient

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        RetrofitClient.initClient()
    }
}