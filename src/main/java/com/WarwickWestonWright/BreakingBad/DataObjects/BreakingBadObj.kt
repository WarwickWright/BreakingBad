package com.WarwickWestonWright.BreakingBad.DataObjects

import android.os.Parcel
import android.os.Parcelable

class BreakingBadObj() : Parcelable {
    private var charId: Int = 0
    private var name: String = ""
    private var birthday: String = ""
    private var occupation: String = ""
    private var img: String = ""
    private var imgB64: String = ""
    private var status: String = ""
    private var nickname: String = ""
    private var appearance: String = ""
    private var portrayed: String = ""
    private var category: String = ""
    private var betterCallSaulAppearance: String = ""

    //6th Parameter B64 will hold base64 image data and persist it to room this is an open end that is not in the data set returned
    constructor(charId: Int,
                name: String,
                birthday: String,
                occupation: String,
                img: String,
                imgB64: String,
                status: String,
                nickname: String,
                appearance: String,
                portrayed: String,
                category: String,
                betterCallSaulAppearance: String) : this() {
        this.charId = charId
        this.name = name
        this.birthday = birthday
        this.occupation = occupation
        this.img = img
        this.imgB64 = imgB64
        this.status = status
        this.nickname = nickname
        this.appearance = appearance
        this.portrayed = portrayed
        this.category = category
        this.betterCallSaulAppearance = betterCallSaulAppearance
    }

    //Accessors
    fun getCharId() : Int {return this.charId}
    fun getName() : String { return this.name}
    fun getBirthday(): String { return this.birthday}
    fun getOccupation(): String { return this.occupation}
    fun getImg(): String { return this.img }
    fun getImgB64(): String { return this.imgB64 }
    fun getStatus(): String { return this.status}
    fun getNickname(): String { return this.nickname}
    fun getAppearance(): String { return this.appearance}
    fun getPortrayed(): String { return this.portrayed}
    fun getCategory(): String { return this.category}
    fun getBetterCallSaulAppearance(): String { return this.betterCallSaulAppearance}

    //Mutators
    fun setCharId(charId: Int) { this.charId = charId }
    fun setName(name: String) { this.name = name }
    fun setBirthday(birthday: String) { this.birthday = birthday }
    fun setOccupation(occupation: String) { this.occupation = occupation }
    fun setImg(img: String) { this.img = img }
    fun setImgB64(imgB64: String) { this.imgB64 = imgB64 }
    fun setStatus(status: String) { this.status = status }
    fun setNickname(nickname: String) { this.nickname = nickname }
    fun setAppearance(appearance: String) { this.appearance = appearance }
    fun setPortrayed(portrayed: String) { this.portrayed = portrayed }
    fun setCategory(category: String) { this.category = category }
    fun setBetterCallSaulAppearance(betterCallSaulAppearance: String) { this.betterCallSaulAppearance = betterCallSaulAppearance }

    //IDE Generated code
    constructor(parcel: Parcel) : this() {
        charId = parcel.readInt()
        name = parcel.readString().toString()
        birthday = parcel.readString().toString()
        occupation = parcel.readString().toString()
        img = parcel.readString().toString()
        imgB64 = parcel.readString().toString()
        status = parcel.readString().toString()
        nickname = parcel.readString().toString()
        appearance = parcel.readString().toString()
        portrayed = parcel.readString().toString()
        category = parcel.readString().toString()
        betterCallSaulAppearance = parcel.readString().toString()
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(charId)
        parcel.writeString(name)
        parcel.writeString(birthday)
        parcel.writeString(occupation)
        parcel.writeString(img)
        parcel.writeString(imgB64)
        parcel.writeString(status)
        parcel.writeString(nickname)
        parcel.writeString(appearance)
        parcel.writeString(portrayed)
        parcel.writeString(category)
        parcel.writeString(betterCallSaulAppearance)
    }
    override fun describeContents(): Int { return 0 }
    companion object CREATOR : Parcelable.Creator<BreakingBadObj> {
        override fun createFromParcel(parcel: Parcel): BreakingBadObj { return BreakingBadObj(parcel) }
        override fun newArray(size: Int): Array<BreakingBadObj?> { return arrayOfNulls(size) }
    }

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