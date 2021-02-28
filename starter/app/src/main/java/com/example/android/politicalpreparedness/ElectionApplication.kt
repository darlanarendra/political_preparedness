package com.example.android.politicalpreparedness

import android.app.Application

class ElectionApplication:Application(){

    companion object {
        lateinit var instance:Application
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}