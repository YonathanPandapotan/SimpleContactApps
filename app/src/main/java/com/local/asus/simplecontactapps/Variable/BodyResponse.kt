package com.local.asus.simplecontactapps.Variable

import com.google.gson.annotations.SerializedName

class BodyResponse {
    @SerializedName("code")
    lateinit var code: String
    @SerializedName("message")
    lateinit var message: String
    @SerializedName("dataKontak")
    lateinit var dataKontak: ArrayList<Kontak>
}