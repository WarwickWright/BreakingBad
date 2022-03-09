package com.WarwickWestonWright.BreakingBad.UI.Lists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.WarwickWestonWright.BreakingBad.DataObjects.BreakingBadObj
import com.WarwickWestonWright.BreakingBad.R
import com.WarwickWestonWright.BreakingBad.Utilities.Base64Utilities
import com.squareup.picasso.Picasso

class BreakingBadViewAdapter(private val breakingBadObjs: MutableList<BreakingBadObj>, private val iBreakingBadViewAdapter: IBreakingBadViewAdapter):
    RecyclerView.Adapter<BreakingBadViewAdapter.ViewHolder>(), View.OnClickListener {

    interface IBreakingBadViewAdapter {
        fun detailCB(breakingBadObj: BreakingBadObj)
    }

    val base64Utilities = Base64Utilities()

    override fun onClick(v: View?) {
        iBreakingBadViewAdapter.detailCB(v?.tag as BreakingBadObj)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.breaking_bad_item, parent, false)
        view.setOnClickListener(this)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val breakingBadObj = breakingBadObjs[position]
        holder.itemView.tag = breakingBadObj
        holder.lbl_char_id.text = breakingBadObj.getCharId().toString()
        holder.lbl_name.text = breakingBadObj.getName()
        val imgB64 = breakingBadObj.getImgB64()
        if(imgB64 == "") {
            Picasso.get().load(breakingBadObj.getImg()).into(holder.img_img)
        }
        else {
            holder.img_img.setImageBitmap(base64Utilities.convertToBitmap(breakingBadObj.getImgB64()))
        }
    }

    override fun getItemCount(): Int = breakingBadObjs.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val lbl_char_id: TextView = view.findViewById(R.id.lbl_char_id)
        val lbl_name: TextView = view.findViewById(R.id.lbl_name)
        val img_img: ImageView = view.findViewById(R.id.img_img)

        override fun toString(): String {
            return super.toString() + " '" + lbl_char_id.text + "'"
        }
    }
}