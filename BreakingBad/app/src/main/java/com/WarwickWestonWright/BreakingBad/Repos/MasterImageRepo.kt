package com.WarwickWestonWright.BreakingBad.Repos

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.WarwickWestonWright.BreakingBad.App.App
import com.WarwickWestonWright.BreakingBad.Comms.OnlineData
import com.WarwickWestonWright.BreakingBad.DataObjects.BreakingBadObj
import com.WarwickWestonWright.BreakingBad.R
import com.WarwickWestonWright.LastFM.Comms.NetUtils

class MasterImageRepo(private var callingActivity: AppCompatActivity) : NetUtils.IConnectionStatus {

    private val app: App = App.getApp() as App
    private val db = app.db
    private var onlineData = OnlineData(callingActivity)
    private var netUtils = NetUtils(this, callingActivity)
    private var iMasterImageRepo = callingActivity as IMasterImageRepo

    fun getBreakingBadImageList() {
        if(!app.rpcCallInProgress) {
            netUtils.setIsNetworkAvailable()
        }
    }

    override fun netStatusSet(isConnected: Boolean) {
        if(isConnected) {
            Thread {
                app.breakingBadObjs = db.BreakingBadDao().getAll()
                val masterDataListSize = app.breakingBadObjs.size - 1
                for(i in 0..masterDataListSize) {
                    if(app.breakingBadObjs[i].getImgB64() == "") {
                        onlineData.setImages(app.breakingBadObjs[i])
                        while (app.rpcCallInProgress) {}//Get one image at a time
                    }
                }
                callingActivity.runOnUiThread {
                    iMasterImageRepo.getImageData("", 0)
                }
            }.start()
        }
        else {
            Toast.makeText(callingActivity, callingActivity.getString(R.string.toast_no_net), Toast.LENGTH_SHORT).show()
        }
    }
}