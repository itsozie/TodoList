package com.example.dolist
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import java.util.*
import kotlin.collections.ArrayList

//initial DB
val NameDB      = "Dolist"
val TableDB     = "Note"
val id          = "id"
val title       = "title"
val description = "des"
val db_version  =  1
var result = ArrayList<Note>()

//Database connection
class database:SQLiteOpenHelper{
    var context: Context? = null

    constructor(
        context: Context?
    ) : super(context, NameDB, null, db_version){
        this.context = context
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
        //tambah table database
        sqLiteDatabase!!.execSQL(
            "CREATE TABLE " + TableDB +
                    " (" + id + " INTEGER PRIMARY KEY,"
                    + title + " TEXT,"
                    + description + " TEXT);"
        )

        Toast.makeText(this.context,"Database Telah Dibuat",Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    //function Store
    fun add (values: ContentValues):Long{
        val db      = this.writableDatabase
        val result  = db.insert(TableDB,null, values)
        db.close()
        return result
    }

    //fetching data from DB
    fun query():ArrayList<Note>{
        val db  = this.readableDatabase
        val query = "SELECT * FROM " + TableDB
        var cursor = db.rawQuery(query,null)
        if (cursor.moveToFirst()){
            do {
                var id = cursor.getInt(cursor.getColumnIndexOrThrow(id))
                var name = cursor.getString(cursor.getColumnIndexOrThrow(title))
                var des = cursor.getString(cursor.getColumnIndexOrThrow(description))
                result.add(Note(id,name,des))
            }while (cursor.moveToNext())
        }
        return result
    }

    //function cek sum size table database
    fun cekSize():Int{
        var counter = 0
        val db = this.readableDatabase
        val query_params = "SELECT * FROM " + TableDB
        val cursor = db!!.rawQuery(query_params, null)
        if (cursor.moveToFirst()){
            do {
                counter++
            }while (cursor.moveToNext())
        }
        return counter
    }

    //function delete
    fun delete(id:Int):Int{
       val db = this.writableDatabase
       val count = db!!.delete(TableDB, "id=?", arrayOf(id.toString()))
       db.close()
       return count
    }

    //function update
    fun update(id: Int,titles: String, des: String):Int{
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(title,titles)
        cv.put(description,des)
        val count = db!!.update(TableDB,cv,"id=?", arrayOf(id.toString()))
        db.close()
        return  count
    }
}