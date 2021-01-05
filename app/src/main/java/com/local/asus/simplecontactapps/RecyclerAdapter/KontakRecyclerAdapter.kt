package com.local.asus.simplecontactapps.RecyclerAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.local.asus.simplecontactapps.R
import com.local.asus.simplecontactapps.Variable.Kontak
import kotlinx.android.synthetic.main.kontak_recycler.view.*

class KontakRecyclerAdapter(val context: Context,
                            val array: ArrayList<Kontak>,
                            val mListener: ItemListener,
                            val mLongListener: ItemListener
                            ): RecyclerView.Adapter<KontakRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(v: View): RecyclerView.ViewHolder(v), View.OnClickListener, View.OnLongClickListener{

        val nama: TextView
        lateinit var kontak: Kontak

        init{
            nama = v.recycler_nama
            v.setOnClickListener(this)
            v.setOnLongClickListener(this)
        }

        fun bind(kontak: Kontak){
            this.kontak = kontak
            nama.text = kontak.nama
        }

        override fun onClick(p0: View?) {
            if(mListener != null){
                mListener.onItemClick(kontak)
            }
        }

        override fun onLongClick(p0: View?): Boolean {
            if(mLongListener != null){
                mLongListener.onLongItemClick(kontak)
            }
            return true
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(context).inflate(R.layout.kontak_recycler, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kontak: Kontak = array.get(position)
        holder.bind(kontak)
    }


    interface ItemListener{
        fun onItemClick(kontak: Kontak)
        fun onLongItemClick(kontak: Kontak)
    }

}