package com.local.asus.simplecontactapps.Variable

import java.io.Serializable

class Kontak: Serializable{

    // TODO menyimpan gambar pada direktori aplikasi sendiri
    // TODO data kontak disimpan pada sqlite

    lateinit var nama: String
    lateinit var phone: String

}