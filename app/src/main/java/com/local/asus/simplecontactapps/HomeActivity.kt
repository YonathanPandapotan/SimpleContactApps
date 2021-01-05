package com.local.asus.simplecontactapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import com.local.asus.simplecontactapps.RecyclerAdapter.KontakRecyclerAdapter
import com.local.asus.simplecontactapps.Variable.Kontak
import kotlinx.android.synthetic.main.home_activity.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class HomeActivity : AppCompatActivity(), KontakRecyclerAdapter.ItemListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        add_contact.setOnClickListener {
            startActivity(Intent(applicationContext, AddContactActivity::class.java))
        }

        val database = SQLiteDatabaseHelper(applicationContext)

        var arr = database.getAll()

        val kontakRecyclerAdapter = KontakRecyclerAdapter(applicationContext, arr, this, this)
        kontak_recycler.adapter = kontakRecyclerAdapter
        kontak_recycler.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)

    }

    override fun onItemClick(kontak: Kontak) {
        intent = Intent(applicationContext, DetailContactActivity::class.java)
        intent.putExtra("kontak", kontak)
        startActivity(intent)
    }

    override fun onLongItemClick(kontak: Kontak) {
        Toast.makeText(applicationContext, "Long Click", Toast.LENGTH_LONG).show()
    }
}
