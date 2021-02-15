package com.local.asus.simplecontactapps.Variable

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Kontak: Serializable{

    // TODO menyimpan gambar pada direktori aplikasi sendiri
    // TODO data kontak disimpan pada sqlite

    @SerializedName("nama")
    lateinit var nama: String
    @SerializedName("phone")
    lateinit var phone: String

}