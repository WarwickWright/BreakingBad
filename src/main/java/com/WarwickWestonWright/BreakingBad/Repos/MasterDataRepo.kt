package com.WarwickWestonWright.BreakingBad.Repos

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.WarwickWestonWright.BreakingBad.App.App
import com.WarwickWestonWright.BreakingBad.Comms.OnlineData
import com.WarwickWestonWright.BreakingBad.Constants.END_POINT
import com.WarwickWestonWright.BreakingBad.Constants.GET_MASTER_DATA
import com.WarwickWestonWright.BreakingBad.DataObjects.BreakingBadObj
import com.WarwickWestonWright.BreakingBad.MainActivity
import com.WarwickWestonWright.LastFM.Comms.NetUtils
import java.net.URL

class MasterDataRepo(private var callingActivity: AppCompatActivity) : NetUtils.IConnectionStatus {

    private val app: App = App.getApp() as App
    private val db = app.db
    private var onlineData = OnlineData(callingActivity)
    private var netUtils = NetUtils(this, callingActivity)

    fun getBreakingBadList() {
        if(!app.rpcCallInProgress) {
            netUtils.setIsNetworkAvailable()
        }
    }

    override fun netStatusSet(isConnected: Boolean) {
        if(isConnected) {
            var data: MutableList<BreakingBadObj>
            Thread {
                data = db.BreakingBadDao().getAll()
                if(data.size == 0) {
                    callingActivity.runOnUiThread {
                        app.setRpcAction(GET_MASTER_DATA)
                        onlineData.getHttpsDataAsString(URL(END_POINT), "GET")
                    }
                }
                else {
                    if(callingActivity::class.simpleName == "MainActivity") {
                        callingActivity.runOnUiThread {
                            (callingActivity as MainActivity).getMasterData("")//Send empty string to trigger UI update from room
                        }
                    }
                }
            }.start()
        }
        else {
            Toast.makeText(callingActivity, "No Net", Toast.LENGTH_SHORT).show()
        }
    }
}