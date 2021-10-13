package com.example.dolist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_listen_custom.view.*

class MainActivity : AppCompatActivity() {
    var createNote: FloatingActionButton? = null
    var listNotes=ArrayList<Note>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNote = findViewById(R.id.btnAdd)
        createNote?.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    //load data by inner class to list view
    fun loadData(){
        var database = database(this)
        listNotes.clear()
        val cursor = database.query()
        listNotes = cursor
        var myNotesAdapter=MyNotesAdapter(this,listNotes)
        note.adapter=myNotesAdapter
    }

    override fun onResume() {
        super.onResume()
        loadData()
        var database = database(this)
       if (database.cekSize() > 0 ) {
           noNotes.visibility=View.GONE
           note.visibility=View.VISIBLE
       }else{
           noNotes.visibility=View.VISIBLE
           note.visibility=View.GONE
       }
    }


    //Buat instance overiding pengambilan id sebagai cursor
    inner class MyNotesAdapter : BaseAdapter {
        var listNotes = ArrayList<Note>()
        var context: Context? = null

        constructor(context: Context, listNotes: ArrayList<Note>) : super() {
            this.listNotes = listNotes
            this.context = context
        }

        override fun getCount(): Int {
            return listNotes.size
        }

        override fun getItem(p0: Int): Any {
            return listNotes[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var myView = layoutInflater.inflate(R.layout.row_listen_custom, null)
            var myNote = listNotes[p0]
            myView.viewTitle.text = myNote.noteName
            myView.ViewDes.text = myNote.noteDes

            myView.icDelete.setOnClickListener {
                var database = database(context)
                database.delete(myNote.noteId!!)
                loadData()
                if(database.cekSize() > 0){
                    noNotes.visibility=View.GONE
                    note.visibility=View.VISIBLE
                }else{
                    noNotes.visibility=View.VISIBLE
                    note.visibility=View.GONE
                }
            }
            myView.icUpdate.setOnClickListener {
                updateNote(myNote)
            }
            return myView
        }
    }
    fun updateNote(note: Note){
        var intent = Intent(this,AddActivity::class.java)
        intent.putExtra("id",note.noteId)
        intent.putExtra("name",note.noteName)
        intent.putExtra("des",note.noteDes)
        startActivity(intent)

    }
}