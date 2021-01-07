package com.local.asus.simplecontactapps

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import kotlinx.android.synthetic.main.toolbar_layout.*

class HomeActivity : AppCompatActivity(), KontakRecyclerAdapter.ItemListener {

    lateinit var miniAlert: AlertDialog.Builder
    lateinit var deleteAlert: AlertDialog.Builder
    lateinit var kontakRecyclerAdapter: KontakRecyclerAdapter
    lateinit var database: SQLiteDatabaseHelper
    lateinit var arr: ArrayList<Kontak>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        setToolbar()
        setRecyclerAndDialog()

        add_contact.setOnClickListener {
            startActivityForResult(Intent(applicationContext, AddContactActivity::class.java), 1)
        }

    }

    fun setRecyclerAndDialog(){
        deleteAlert = AlertDialog.Builder(this)
        miniAlert = AlertDialog.Builder(this)
        database = SQLiteDatabaseHelper(applicationContext)
        arr = database.getAll()

        kontakRecyclerAdapter = KontakRecyclerAdapter(applicationContext, arr, this, this)
        kontak_recycler.adapter = kontakRecyclerAdapter
        kontak_recycler.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
    }

    fun setToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar_title.text = "Kontak"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode){
            1 ->{
                arr.clear()
                kontakRecyclerAdapter.notifyDataSetChanged()
                for(kontak in database.getAll()){
                    arr.add(kontak)
                    kontakRecyclerAdapter.notifyItemInserted(arr.size-1)
                }
            }
        }

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
                val database = SQLiteDatabaseHelper(applicationContext)

                database.deleteContact(kontak)
                kontakRecyclerAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }

            if (dialog.window != null)
                dialog.window!!.getAttributes().windowAnimations = R.style.SlidingDialogAnimation

            dialog.show()
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        if (dialog2.window != null)
            dialog2.window!!.getAttributes().windowAnimations = R.style.MiniMenuAnimation

        dialog2.show()
        dialog2.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }
}
