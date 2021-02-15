package com.local.asus.simplecontactapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.local.asus.simplecontactapps.Variable.Kontak
import com.local.asus.simplecontactapps.databinding.AddContactActivityBinding
import com.local.asus.simplecontactapps.databinding.ToolbarLayoutBinding

class AddContactActivity : AppCompatActivity() {
    var angka = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var database = SQLiteDatabaseHelper(applicationContext)

        val binding = AddContactActivityBinding.inflate(layoutInflater)
        setSupportActionBar(binding.includedLayout.toolbar)
        setToolbar(binding.includedLayout)

        binding.simpanButton.setOnClickListener {
            var kontak = Kontak()
            kontak.nama = binding.namaKontak.text.toString()
            kontak.phone = binding.nomorKontak.text.toString()
            database.addContact(kontak)

            Toast.makeText(applicationContext, "Berhasil", Toast.LENGTH_LONG).show()
            this.finish()
        }

        setContentView(binding.root)

    }

    fun setToolbar(includedLayout: ToolbarLayoutBinding) {
        setSupportActionBar(includedLayout.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        includedLayout.toolbarTitle.setText("Kontak Baru")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
