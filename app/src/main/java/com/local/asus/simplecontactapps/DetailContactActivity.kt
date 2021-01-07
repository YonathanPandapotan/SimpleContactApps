package com.local.asus.simplecontactapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.local.asus.simplecontactapps.Variable.Kontak
import kotlinx.android.synthetic.main.detail_contact_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class DetailContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_contact_activity)

        setToolbar()

        var kontak = intent.getSerializableExtra("kontak") as Kontak
        nama_kontak_detail.text = kontak.nama
        no_telp_detail.text = kontak.phone

    }

    fun setToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
