package com.example.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context ): SQLiteOpenHelper(context, DB_NAME,null,1) {

    override fun onCreate(db: SQLiteDatabase?) {
        var Create_Table=("CREATE TABLE $TABLE_NAME ($Col_ID INTEGER PRIMARY KEY ,"+
                "$Col_PHONE TEXT)")
        db?.execSQL(Create_Table)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        var Drop_Table="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(Drop_Table)
        onCreate(db)
    }

    companion object{
        val DB_NAME = "contact_info"
        val TABLE_NAME = "contact_info"
        val Col_ID = "ID"
        val Col_PHONE = "PHONE"
    }

    fun insert(phone:String){
        var db = writableDatabase
        val value= ContentValues()
        value.put(Col_PHONE,phone)
        db.insert(TABLE_NAME,null,value)
    }

    fun update(phone:String, old_phone:String){
        var db = writableDatabase
        val value= ContentValues()
        value.put(Col_PHONE,phone)
        db.update(TABLE_NAME,value,"PHONE ="+old_phone,null)
        db.close()
    }

    @SuppressLint("Range")
    fun viewPhone() : List<String>{
        var list:ArrayList<String> = ArrayList()
        var query= "SELECT * FROM $TABLE_NAME"
        var db= readableDatabase
        var cursor:Cursor?=null

        try {
           cursor=db.rawQuery(query,null)

        }catch (e:SQLException){
            db.execSQL(query)
            return list
        }
        var phone:String
        if (cursor.moveToNext()){
            do {
                phone= cursor.getString(cursor.getColumnIndex("PHONE"))
                list.add(phone)
            }while (cursor.moveToNext())
        }

        return list
    }


    fun delete(phone:String){
        var db = writableDatabase
        db.delete(TABLE_NAME,"PHONE =  $phone", null)
    }
















}