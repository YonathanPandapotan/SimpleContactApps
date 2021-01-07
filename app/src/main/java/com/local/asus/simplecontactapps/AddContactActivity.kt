package com.local.asus.simplecontactapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.local.asus.simplecontactapps.RecyclerAdapter.KontakRecyclerAdapter
import com.local.asus.simplecontactapps.Variable.Kontak
import kotlinx.android.synthetic.main.add_contact_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class AddContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_contact_activity)

        setToolbar()

        var database = SQLiteDatabaseHelper(applicationContext)

        simpan_button.setOnClickListener{
            var kontak = Kontak()
            kontak.nama = nama_kontak.text.toString()
            kontak.phone = nomor_kontak.text.toString()
            database.addContact(kontak)

            Toast.makeText(applicationContext, "Berhasil", Toast.LENGTH_LONG).show()
            this.finish()

        }
    }

    fun setToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar_title.text = "Kontak Baru"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
