package com.WarwickWestonWright.BreakingBad.App

import android.app.Application
import androidx.room.Room
import com.WarwickWestonWright.BreakingBad.DataObjects.BreakingBadObj
import com.WarwickWestonWright.BreakingBad.Room.AppDatabase

class App: Application() {
    private var action = 0
    lateinit var db: AppDatabase
    var rpcCallInProgress = false
    var breakingBadObjs: MutableList<BreakingBadObj> = mutableListOf()

    override fun onCreate() {
        super.onCreate()
        breakingBadContext = this
        initSingletons()
    }

    fun initSingletons() {
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "MyBreakingBadLib").build()
    }

    /*
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTerminate() {
        super.onTerminate()
    }
    * */

    fun getRpcAction() : Int {
        return action
    }

    fun setRpcAction(action: Int) {
        this.action = action
    }

    companion object {
        @JvmStatic
        lateinit var breakingBadContext: Application
        @JvmStatic
        fun getApp() : Application {
            return breakingBadContext
        }
    }
}