package com.WarwickWestonWright.BreakingBad

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.WarwickWestonWright.BreakingBad.App.App
import com.WarwickWestonWright.BreakingBad.Constants.GET_IMAGE
import com.WarwickWestonWright.BreakingBad.Constants.GET_MASTER_DATA
import com.WarwickWestonWright.BreakingBad.DataObjects.BreakingBadObj
import com.WarwickWestonWright.BreakingBad.DeSerializers.DeSerializers
import com.WarwickWestonWright.BreakingBad.DeSerializers.ObjToEntities
import com.WarwickWestonWright.BreakingBad.Repos.IMasterDataRepo
import com.WarwickWestonWright.BreakingBad.Repos.IMasterImageRepo
import com.WarwickWestonWright.BreakingBad.Repos.MasterDataRepo
import com.WarwickWestonWright.BreakingBad.Repos.MasterImageRepo
import com.WarwickWestonWright.BreakingBad.UI.BreakingBadDetailFragment
import com.WarwickWestonWright.BreakingBad.UI.Lists.BreakingBadViewAdapter
import com.WarwickWestonWright.BreakingBad.Utilities.BreakingBadSearchFilters
import com.WarwickWestonWright.BreakingBad.ViewModels.BreakingBadObjViewModel
import com.WarwickWestonWright.LastFM.Comms.NetUtils

class MainActivity : AppCompatActivity(),
        NetUtils.IConnectionStatus,
        IMasterDataRepo,
        IMasterImageRepo,
        BreakingBadViewAdapter.IBreakingBadViewAdapter{

    private val app: App = App.getApp() as App
    private lateinit var netUtils: NetUtils
    private var isConnected = false
    private lateinit var masterDataRepo: MasterDataRepo
    private lateinit var masterImageRepo: MasterImageRepo
    private val deSerializers = DeSerializers()
    private val db = app.db
    private val breakingBadSearchFilters = BreakingBadSearchFilters()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        breakingBadObjViewModel = ViewModelProvider(this).get(BreakingBadObjViewModel::class.java)
        app.setRpcAction(GET_MASTER_DATA)
        netUtils = NetUtils(this, this)
    }

    override fun onResume() {
        super.onResume()
        if(!app.rpcCallInProgress) {
            netUtils.setIsNetworkAvailable()
        }
    }

    override fun netStatusSet(isConnected: Boolean) {
        this.isConnected = isConnected
        if(isConnected) {
            if(app.getRpcAction() == GET_MASTER_DATA) {
                masterDataRepo = MasterDataRepo(this)
                masterDataRepo.getBreakingBadList()
            }
            else if(app.getRpcAction() == GET_IMAGE) {
                masterImageRepo = MasterImageRepo(this)
                masterImageRepo.getBreakingBadImageList()
            }
        }
        else {
            Toast.makeText(this, getString(R.string.toast_no_net), Toast.LENGTH_LONG).show()
        }
    }

    override fun getMasterData(jsonStr: String) {
        if(jsonStr != "") {
            if(app.breakingBadObjs.size > 0) { return }
            app.breakingBadObjs = deSerializers.deserializeImageLibrary(jsonStr)
            breakingBadObjViewModel.selected.value = app.breakingBadObjs
            Thread {
                val objToEntity = ObjToEntities()
                for (breakingBadObj in app.breakingBadObjs) {
                    val breakingBadEntity = objToEntity.breakingBadObjToEntity(breakingBadObj)
                    db.BreakingBadDao().insertAll(breakingBadEntity)
                }
                if(!app.rpcCallInProgress) {
                    app.setRpcAction(GET_IMAGE)
                    netUtils.setIsNetworkAvailable()
                }
            }.start()
        }
        else {
            Thread {
                app.breakingBadObjs = db.BreakingBadDao().getAll()
                if(!breakingBadSearchFilters.allImagesPersisted(app.breakingBadObjs)) {
                    runOnUiThread {
                        breakingBadObjViewModel.selected.value = app.breakingBadObjs
                        if(app.breakingBadObjs.size > 0) {
                            if(!app.rpcCallInProgress) {
                                app.setRpcAction(GET_IMAGE)
                                netUtils.setIsNetworkAvailable()
                            }
                        }
                    }
                }
            }.start()
        }
    }

    companion object {
        lateinit var breakingBadObjViewModel: BreakingBadObjViewModel
    }

    override fun getImageData(base64: String, imgId: Int) {
        Thread {
            app.breakingBadObjs = db.BreakingBadDao().getAll()
            runOnUiThread {
                breakingBadObjViewModel.selected.value = app.breakingBadObjs
                //breakingBadObjViewModel.select(app.breakingBadObjs)
            }
        }.start()
    }

    override fun detailCB(breakingBadObj: BreakingBadObj) {
        val bundle = Bundle()
        bundle.putParcelable("breakingBadObj", breakingBadObj)
        val breakingBadDetailFragment = BreakingBadDetailFragment()
        breakingBadDetailFragment.arguments = bundle
        breakingBadDetailFragment.show(supportFragmentManager, "BreakingBadDetailFragment")
    }

}