package com.local.asus.simplecontactapps


import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.local.asus.simplecontactapps.RecyclerAdapter.KontakRecyclerAdapter
import com.local.asus.simplecontactapps.Variable.Kontak
import kotlinx.android.synthetic.main.home_activity.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.alert_dialog.view.*
import java.lang.Exception

class HomeActivity : AppCompatActivity(), KontakRecyclerAdapter.ItemListener {

    lateinit var alertDialog: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        alertDialog = AlertDialog.Builder(this)

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
        var layout = layoutInflater.inflate(R.layout.alert_dialog, null)
        alertDialog.setView(layout)
        var dialog = alertDialog.create()
        layout.alert_cancel.setOnClickListener {
            Toast.makeText(applicationContext, "Batal hapus", Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }
        layout.alert_confirm.setOnClickListener {
            Toast.makeText(applicationContext, "Hapus", Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }

        if (dialog.window != null)
            dialog.window!!.getAttributes().windowAnimations = R.style.SlidingDialogAnimation

        dialog.show()
    }
}
