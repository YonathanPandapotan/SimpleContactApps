package com.local.asus.simplecontactapps

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.local.asus.simplecontactapps.Variable.Kontak

class SQLiteDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "simple_contact", null, 1){

    override fun onCreate(p0: SQLiteDatabase) {
        p0.execSQL("CREATE TABLE kontak (nama TEXT, phone TEXT)")
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
        p0.execSQL("DROP TABLE IF EXISTS kontak")
    }

    fun addContact(kontak: Kontak){
        val db = this.writableDatabase
        val contentValue = ContentValues().apply {
            put("nama", kontak.nama)
            put("phone", kontak.phone)
        }
        db.insert("kontak", null, contentValue)
        db.close()
    }

    fun deleteContact(kontak: Kontak){
        val db = this.writableDatabase
        val selectionArgs = arrayOf(kontak.nama)
        db.delete("kontak", "nama LIKE ?", selectionArgs)
    }

    fun getAll(): ArrayList<Kontak>{
        var arr = ArrayList<Kontak>()
        val db = this.writableDatabase

        val cursor = db.rawQuery("SELECT * FROM kontak", null)

        if(cursor.moveToFirst()){
            do{
                var kontak = Kontak()
                kontak.nama = cursor.getString(0)
                kontak.phone = cursor.getString(1)
                arr.add(kontak)
            }while (cursor.moveToNext())

            arr = ArrayList(arr.sortedBy { it.nama })
        }

        return arr
    }

}