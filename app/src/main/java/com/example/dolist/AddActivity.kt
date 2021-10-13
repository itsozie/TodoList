package com.example.dolist

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        title = "Add Notes"
        try {
            var  bundle:Bundle?=intent.extras
            id = bundle!!.getInt("id",0)
            if (id!=0){
                enTitle.setText(bundle.getString("name"))
                enDes.setText(bundle.getString("des"))
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    // function menambahkan note di database
    fun btnAdd(view: View) {
        //inisialisasi databse
        val database = database(this)
        var values = ContentValues()
        //input ke variable title deskripsi
        if(enTitle.text.length>0) {
            values.put("title", enTitle.text.toString())
            values.put("des", enDes.text.toString())
        }
        else{
            enTitle.setError("Wajib Memasukkan Title terlebih Dahulu !!")
            enTitle.requestFocus()
            return
        }
        if (id==0){
            val ID = database.add(values)
            if (ID > 0){
                Toast.makeText(this,"Catatan telah ditambahkan",Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Tidak Bisa Menambahkan Catatan",Toast.LENGTH_SHORT).show()
            }
        }else{
            val ID = database.update(id,enTitle.text.toString(),enDes.text.toString())
            if(ID>0){
                Toast.makeText(this, "Catatan Telah Ditambahkan !!",Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this, "Catatan Gagal Ditambahkan !!",Toast.LENGTH_SHORT).show()
            }
        }
    }

}