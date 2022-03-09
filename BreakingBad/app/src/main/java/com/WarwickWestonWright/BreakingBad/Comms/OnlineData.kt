package com.WarwickWestonWright.BreakingBad.Comms

import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.WarwickWestonWright.BreakingBad.App.App
import com.WarwickWestonWright.BreakingBad.Constants.GET_IMAGE
import com.WarwickWestonWright.BreakingBad.Constants.GET_MASTER_DATA
import com.WarwickWestonWright.BreakingBad.DataObjects.BreakingBadObj
import com.WarwickWestonWright.BreakingBad.Repos.IMasterDataRepo
import com.WarwickWestonWright.BreakingBad.Repos.IMasterImageRepo
import com.WarwickWestonWright.BreakingBad.Utilities.Base64Utilities
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.ref.WeakReference
import java.net.URL
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.zip.GZIPInputStream
import javax.net.ssl.*

class OnlineData(var callingActivity: AppCompatActivity): Runnable {
    private val onlineDataRef: WeakReference<OnlineData> = WeakReference(this)
    private var onlineDataHandler: OnlineDataHandler? = null
    private val app = App.getApp() as App
    private lateinit var url: URL
    private var verb: String? = null
    private var dataThread: Thread? = null
    private var imageThread: Thread? = null
    private var methodName = ""

    //private val allHostsValid = HostnameVerifier { hostname, session -> true }//Use this when testing with unrecognized cert

    private val localTrustmanager: TrustManager = object : X509TrustManager {
        override fun getAcceptedIssuers(): Array<X509Certificate>? { return null }
        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
    }
    private val trustAllCerts = arrayOf(localTrustmanager)

    fun getHttpsDataAsString(url: URL, verb: String) {
        onlineDataHandler = OnlineDataHandler(onlineDataRef, callingActivity)
        methodName = "getHttpsDataAsString"
        this.url = url
        this.verb = verb?.toUpperCase()
        dataThread = Thread(this)
        app.rpcCallInProgress = true
        dataThread?.start()
    }

    fun setImages(breakingBadObj: BreakingBadObj) {
        val base64Utilities = Base64Utilities()
        app.rpcCallInProgress = true
        val bitmap = base64Utilities.base64ImageFromURL(URL(breakingBadObj.getImg()), "GET")
        if(bitmap.width > bitmap.height) {
            if(bitmap.width > 350) {
                val ratio = bitmap.width.toDouble() / (350).toDouble()
                val resized = Bitmap.createScaledBitmap(bitmap, 350, (bitmap.height.toDouble() / ratio).toInt(), true)
                app.db.BreakingBadDao().updateImgB64(base64Utilities.convertToBase64(resized), breakingBadObj.getCharId())
            }
        }
        else if(bitmap.height >= bitmap.width) {
            if(bitmap.height > 350) {
                val ratio = bitmap.height.toDouble() / (350).toDouble()
                val resized = Bitmap.createScaledBitmap(bitmap, (bitmap.width.toDouble() / ratio).toInt(), 350, true)
                app.db.BreakingBadDao().updateImgB64(base64Utilities.convertToBase64(resized), breakingBadObj.getCharId())
            }
        }
        app.rpcCallInProgress = false
    }

    fun getHttpsImage(url: URL, verb: String, imgId: Int) {
        onlineDataHandler = OnlineDataHandler(onlineDataRef, callingActivity)
        methodName = "getHttpsImage"
        this.url = url
        this.verb = verb?.toUpperCase()
        imageThread = Thread(this)
        imageThread?.name = imgId.toString()
        app.rpcCallInProgress = true
        imageThread?.start()
    }

    override fun run() {
        if(methodName == "getHttpsDataAsString") {
            getHttpsDataAsString()
        }
        else if(methodName == "getHttpsImage") {
            getHttpsImage()
        }
    }

    private fun getHttpsDataAsString() {
        var httpsURLConnection: HttpsURLConnection? = null
        val msg: Message = Message()
        val bufferedReader: BufferedReader
        val sb: StringBuilder
        var sc: SSLContext? = null
        try {
            sc = SSLContext.getInstance("TLSv1.2")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        try {
            sc?.init(null, trustAllCerts, SecureRandom())
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }
        try {
            httpsURLConnection = url?.openConnection() as HttpsURLConnection
            httpsURLConnection.sslSocketFactory = sc!!.socketFactory
            httpsURLConnection.hostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier()
            //httpsURLConnection.hostnameVerifier = allHostsValid//Use this when testing with unrecognized cert
            if(verb?.toUpperCase() == "POST") {
                httpsURLConnection?.doInput = true
                httpsURLConnection?.doOutput = true
                httpsURLConnection?.useCaches = true
            }
            else if(verb?.toUpperCase() == "GET") {
                httpsURLConnection?.doInput = true
                httpsURLConnection?.doOutput = false
                httpsURLConnection?.useCaches = true
            }
            httpsURLConnection?.requestMethod = verb
            httpsURLConnection?.readTimeout = 15 * 1000
            //application/json; charset=UTF-8
            //httpsURLConnection?.setRequestProperty("Accept-Encoding", "x-www-form-urlencoded")
            httpsURLConnection?.setRequestProperty("Accept-Encoding", "gzip")
            //httpsURLConnection?.setRequestProperty("Accept-Encoding", "json")
            //httpsURLConnection?.setRequestProperty("Accept-Charset", "UTF-8")
            httpsURLConnection?.connect()
            //bufferedReader = BufferedReader(InputStreamReader(httpsURLConnection?.inputStream))
            bufferedReader = BufferedReader(InputStreamReader(GZIPInputStream(httpsURLConnection?.inputStream)))
            sb = StringBuilder()
            var line: String?
            bufferedReader.useLines { lines ->
                lines.forEach { line ->
                    sb.append(line)
                }
            }
            bufferedReader.close()
            httpsURLConnection?.inputStream?.close()
            msg.obj = sb.toString()
            onlineDataHandler?.handleMessage(msg)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            httpsURLConnection?.disconnect()
        }
    }

    private fun getHttpsImage() {
        val msg = Message()
        val base64Utilities = Base64Utilities()
        app.rpcCallInProgress = true
        val base64Image = base64Utilities.convertToBase64(base64Utilities.base64ImageFromURL(url, "GET"))
        msg.obj = base64Image
        onlineDataHandler?.handleMessage(msg)
    }

    companion object {

        class OnlineDataHandler(private var onlineDataRef: WeakReference<OnlineData>, var callingActivity: AppCompatActivity) : Handler(callingActivity.mainLooper) {
            override fun handleMessage(msg: Message) {
                val oldRef: OnlineData? = onlineDataRef.get()
                if(oldRef != null) {
                    Looper.prepare()
                    post {
                        if(oldRef.app.getRpcAction() == GET_MASTER_DATA) {
                            oldRef.dataThread = null
                            if(oldRef.callingActivity::class.simpleName == "MainActivity") {
                                val iMasterDataRepo = callingActivity as IMasterDataRepo
                                iMasterDataRepo.getMasterData(msg.obj as String)
                            }
                        }
                        else if(oldRef.app.getRpcAction() == GET_IMAGE) {
                            if(oldRef.callingActivity::class.simpleName == "MainActivity") {
                                val iMasterImageRepo = callingActivity as IMasterImageRepo
                                iMasterImageRepo.getImageData(msg.obj as String, oldRef.imageThread?.name!!.toInt())
                            }
                            oldRef.imageThread = null
                        }
                        oldRef.app.rpcCallInProgress = false
                    }
                    Looper.loop()
                }
            }
        }

    }
}