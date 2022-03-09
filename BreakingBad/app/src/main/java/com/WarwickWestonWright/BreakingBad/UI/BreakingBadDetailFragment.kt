package com.WarwickWestonWright.BreakingBad.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.WarwickWestonWright.BreakingBad.DataObjects.BreakingBadObj
import com.WarwickWestonWright.BreakingBad.R
import com.WarwickWestonWright.BreakingBad.Utilities.Base64Utilities
import com.squareup.picasso.Picasso

class BreakingBadDetailFragment : DialogFragment() {

    private lateinit var rootView: View
    private lateinit var lblNameDetail: TextView
    private lateinit var imgImageDetail: ImageView
    private lateinit var lblOccupationDetail: TextView
    private lateinit var lblStatusDetail: TextView
    private lateinit var lblNicknameDetail: TextView
    private lateinit var lblSeasonAppearanceDetail: TextView
    private lateinit var btnCloseDetail:  Button

    val base64Utilities = Base64Utilities()

    private var breakingBadObj: BreakingBadObj? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            breakingBadObj = bundle.getParcelable("breakingBadObj")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.breaking_bad_detail_fragment, container, false)

        lblNameDetail = rootView.findViewById(R.id.lblNameDetail)
        imgImageDetail = rootView.findViewById(R.id.imgImageDetail)
        lblOccupationDetail = rootView.findViewById(R.id.lblOccupationDetail)
        lblStatusDetail = rootView.findViewById(R.id.lblStatusDetail)
        lblNicknameDetail = rootView.findViewById(R.id.lblNicknameDetail)
        lblSeasonAppearanceDetail = rootView.findViewById(R.id.lblSeasonAppearanceDetail)
        btnCloseDetail = rootView.findViewById(R.id.btnCloseDetail)

        lblNameDetail.text = breakingBadObj?.getName()

        if(breakingBadObj?.getImgB64() == "") {
            Picasso.get().load(breakingBadObj?.getImg()).into(imgImageDetail)
        }
        else {
            imgImageDetail.setImageBitmap(base64Utilities.convertToBitmap(breakingBadObj?.getImgB64()!!))
        }

        lblOccupationDetail.text = "Occupation: " + breakingBadObj?.getOccupation()
        lblStatusDetail.text = "Status: " + breakingBadObj?.getStatus()
        lblNicknameDetail.text = "AKA: " + breakingBadObj?.getNickname()
        lblSeasonAppearanceDetail.text = "Season Appearance: " + breakingBadObj?.getAppearance()

        btnCloseDetail.setOnClickListener {
            dialog?.dismiss()
        }

        return rootView
    }

}