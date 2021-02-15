package com.local.asus.simplecontactapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.local.asus.simplecontactapps.Variable.Kontak
import com.local.asus.simplecontactapps.databinding.DetailContactActivityBinding

class DetailContactActivity : AppCompatActivity() {

    lateinit var layoutBinding: DetailContactActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layoutBinding = DetailContactActivityBinding.inflate(layoutInflater)

        setContentView(layoutBinding.root)

        setToolbar()

        var kontak = intent.getSerializableExtra("kontak") as Kontak
        layoutBinding.namaKontakDetail.text = kontak.nama
        layoutBinding.noTelpDetail.text = kontak.phone

    }

    fun setToolbar(){
        setSupportActionBar(layoutBinding.includedLayout.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
