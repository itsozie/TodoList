package com.example.dolist

import java.util.*

class Note {
    var noteId:Int?      =   null
    var noteName:String? =   null
    var noteDes:String?  =   null

    constructor(noteId:Int, noteName:String, noteDes:String){
        this.noteId = noteId
        this.noteName = noteName
        this.noteDes = noteDes
    }
}