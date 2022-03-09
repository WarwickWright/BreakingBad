package com.WarwickWestonWright.LastFM.Comms

class NameValuePair {
    /* Accessors *//* Mutators */
    var name: String
    var value: String

    constructor() {
        name = ""
        value = ""
    }

    constructor(name: String, value: String) {
        this.name = name
        this.value = value
    }
}