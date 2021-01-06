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
import kotlinx.android.synthetic.main.kontak_mini_menu.view.*

class HomeActivity : AppCompatActivity(), KontakRecyclerAdapter.ItemListener {

    lateinit var miniAlert: AlertDialog.Builder
    lateinit var deleteAlert: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        deleteAlert = AlertDialog.Builder(this)
        miniAlert = AlertDialog.Builder(this)

        add_contact.setOnClickListener {
            startActivity(Intent(applicationContext, AddContactActivity::class.java))
        }

        val database = SQLiteDatabaseHelper(applicationContext)

        var arr = database.getAll()

        val kontakRecyclerAdapter = KontakRecyclerAdapter(applicationContext, arr, this, this)
        kontak_recycler.adapter = kontakRecyclerAdapter
        kontak_recycler.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)

    }

    fun startDetailActivity(kontak: Kontak){
        intent = Intent(applicationContext, DetailContactActivity::class.java)
        intent.putExtra("kontak", kontak)
        startActivity(intent)
    }

    override fun onItemClick(kontak: Kontak) {
        startDetailActivity(kontak)
    }

    override fun onLongItemClick(kontak: Kontak) {
        var miniLayout = layoutInflater.inflate(R.layout.kontak_mini_menu, null)
        miniLayout.mini_menu_nama.text = kontak.nama
        miniLayout.mini_menu_nomor.text = kontak.phone


        miniAlert.setView(miniLayout)
        var dialog2 = miniAlert.create()

        miniLayout.mini_menu_lihat.setOnClickListener {
            startDetailActivity(kontak)
            dialog2.dismiss()
        }

        miniLayout.mini_menu_hapus.setOnClickListener {
            dialog2.dismiss()
            var layout = layoutInflater.inflate(R.layout.alert_dialog, null)
            deleteAlert.setView(layout)
            var dialog = deleteAlert.create()
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

        if (dialog2.window != null)
            dialog2.window!!.getAttributes().windowAnimations = R.style.MiniMenuAnimation

        dialog2.show()
        dialog2.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)


//        var layout = layoutInflater.inflate(R.layout.alert_dialog, null)
//        deleteAlert.setView(layout)
//        var dialog = deleteAlert.create()
//        layout.alert_cancel.setOnClickListener {
//            Toast.makeText(applicationContext, "Batal hapus", Toast.LENGTH_LONG).show()
//            dialog.dismiss()
//        }
//        layout.alert_confirm.setOnClickListener {
//            Toast.makeText(applicationContext, "Hapus", Toast.LENGTH_LONG).show()
//            dialog.dismiss()
//        }

//        if (dialog.window != null)
//            dialog.window!!.getAttributes().windowAnimations = R.style.SlidingDialogAnimation
//
//        dialog.show()
    }
}
