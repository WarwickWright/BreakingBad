package com.WarwickWestonWright.BreakingBad.DeSerializers

import com.WarwickWestonWright.BreakingBad.DataObjects.BreakingBadObj
import org.json.JSONArray

class DeSerializers {

    fun deserializeImageLibrary(jsonStr: String?) : MutableList<BreakingBadObj> {
        val returnVal = mutableListOf<BreakingBadObj>()
        val jsa = JSONArray(jsonStr)
        val jsaLen = jsa.length()
        for (i in 0 until jsaLen) {
            val item = jsa.getJSONObject(i)
            val breakingBadObj = BreakingBadObj(
                    item.getInt("char_id"),
                    item.getString("name"),
                    item.getString("birthday"),
                    item.getJSONArray("occupation").toString(),
                    item.getString("img"),
                    "",
                    item.getString("status"),
                    item.getString("nickname"),
                    item.getJSONArray("appearance").toString(),
                    item.getString("portrayed"),
                    item.getString("category"),
                    item.getJSONArray("better_call_saul_appearance").toString()
            )
            //6th Parameter B64 will hold base64 image data and persist it to room this is an open end that is not in the data set returned
            breakingBadObj.setCharId(item.getString("char_id").toInt())
            returnVal.add(breakingBadObj)
        }
        return returnVal
    }

/*
[{
    "char_id": 1,
    "name": "Walter White",
    "birthday": "09-07-1958",
    "occupation": ["High School Chemistry Teacher", "Meth King Pin"],
    "img": "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
    "status": "Presumed dead",
    "nickname": "Heisenberg",
    "appearance": [1, 2, 3, 4, 5],
    "portrayed": "Bryan Cranston",
    "category": "Breaking Bad",
    "better_call_saul_appearance": []
}, { .... )]
* */
}