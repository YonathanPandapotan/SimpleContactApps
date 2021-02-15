package com.local.asus.simplecontactapps

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.local.asus.simplecontactapps.RecyclerAdapter.KontakRecyclerAdapter
import com.local.asus.simplecontactapps.Variable.Kontak
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.local.asus.simplecontactapps.Variable.ApiCall
import com.local.asus.simplecontactapps.Variable.ApiConnection
import com.local.asus.simplecontactapps.Variable.BodyResponse
import com.local.asus.simplecontactapps.databinding.AlertDialogBinding
import com.local.asus.simplecontactapps.databinding.HomeActivityBinding
import com.local.asus.simplecontactapps.databinding.KontakMiniMenuBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity(), KontakRecyclerAdapter.ItemListener {

    lateinit var miniAlert: AlertDialog.Builder
    lateinit var deleteAlert: AlertDialog.Builder
    lateinit var kontakRecyclerAdapter: KontakRecyclerAdapter
    lateinit var database: SQLiteDatabaseHelper
    lateinit var arr: ArrayList<Kontak>
    lateinit var apiCal: ApiCall
    lateinit var layoutBinding: HomeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layoutBinding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(layoutBinding.root)

//        setContentView(R.layout.home_activity)

        apiCal = ApiConnection.getConnection().create(ApiCall::class.java)

        setToolbar()
        setRecyclerAndDialog()

        layoutBinding.addContact.setOnClickListener {
            startActivityForResult(Intent(applicationContext, AddContactActivity::class.java), 1)
        }

    }

    fun setRecyclerAndDialog(){
        deleteAlert = AlertDialog.Builder(this)
        miniAlert = AlertDialog.Builder(this)
        database = SQLiteDatabaseHelper(applicationContext)
        arr = database.getAll()

        kontakRecyclerAdapter = KontakRecyclerAdapter(applicationContext, arr, this, this)

        layoutBinding.kontakRecycler.adapter = kontakRecyclerAdapter
        layoutBinding.kontakRecycler.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
    }

    fun setToolbar(){
        setSupportActionBar(layoutBinding.includedLayout.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        layoutBinding.includedLayout.toolbarTitle.text = "Kontak"
    }

    fun updateRecycler(){
        arr.clear()
        kontakRecyclerAdapter.notifyDataSetChanged()
        for(kontak in database.getAll()){
            arr.add(kontak)
            kontakRecyclerAdapter.notifyItemInserted(arr.size-1)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
             R.id.upload -> {
                 var kontakArr = database.getAll()
                 var call: Call<BodyResponse> = apiCal.uploadAll(kontakArr)
                 call.enqueue(object: Callback<BodyResponse>{
                     override fun onFailure(call: Call<BodyResponse>, t: Throwable) {
                         Toast.makeText(applicationContext, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                         Log.d("err", t.toString())
                     }

                     override fun onResponse(
                         call: Call<BodyResponse>,
                         response: Response<BodyResponse>
                     ) {
                         Toast.makeText(applicationContext, "Uploaded", Toast.LENGTH_SHORT).show()
                     }

                 })
            }
            R.id.update -> {

            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode){
            1 ->{
                updateRecycler()
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
        // Trying to change popup long click into view binding

        var popupBinding = KontakMiniMenuBinding.inflate(layoutInflater)
        popupBinding.miniMenuNama.text = kontak.nama
        popupBinding.miniMenuNomor.text = kontak.phone

        var miniLayout = layoutInflater.inflate(R.layout.kontak_mini_menu, null)
//        miniLayout.mini_menu_nama.text = kontak.nama
//        miniLayout.mini_menu_nomor.text = kontak.phone

        miniAlert.setView(popupBinding.root)
        var dialog2 = miniAlert.create()

        popupBinding.miniMenuEdit.setOnClickListener {
            startDetailActivity(kontak)
            dialog2.dismiss()
        }

        popupBinding.miniMenuHapus.setOnClickListener {
            dialog2.dismiss()

            var popupAlert = AlertDialogBinding.inflate(layoutInflater)

            var layout = layoutInflater.inflate(R.layout.alert_dialog, null)
            deleteAlert.setView(popupAlert.root)
            var dialog = deleteAlert.create()
            popupAlert.alertCancel.setOnClickListener {
                Toast.makeText(applicationContext, "Batal hapus", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
            popupAlert.alertConfirm.setOnClickListener {
                val database = SQLiteDatabaseHelper(applicationContext)

                database.deleteContact(kontak)
                updateRecycler()
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
