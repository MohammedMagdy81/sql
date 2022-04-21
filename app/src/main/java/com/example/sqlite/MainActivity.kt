package com.example.sqlite

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.sql.SQLDataException
import java.sql.SQLException

class MainActivity : AppCompatActivity() {
    lateinit var listView:ListView
    var dbHelper= DBHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView= findViewById(R.id.listView)
        listView.setOnItemClickListener{parent,view,position,id->
            var insert_name=EditText(this)
            val itemValue= listView.getItemAtPosition(position) as String
            val dialog= AlertDialog.Builder(view.context)
            dialog.setTitle("تعديل")
            dialog.setView(insert_name)
            dialog.setPositiveButton("تحديث ", ){dialog,id->
                if (insert_name.text.toString().isEmpty()){
                    Toast.makeText(this, "الرجاء إدخال قيمة", Toast.LENGTH_LONG).show()
                }else{
                    dbHelper.update(insert_name.text.toString(),itemValue)
                }

            }
            dialog.setNegativeButton("حذف"){dialog,id->
                try {
                    dbHelper.delete(itemValue)
                    Toast.makeText(this, "تم الحذف بنجاح", Toast.LENGTH_LONG).show()
                }catch (e: SQLDataException){
                    Toast.makeText(this, "حدث خطأ", Toast.LENGTH_LONG).show()
                }
            }
            dialog.show()

        }
    }

    fun insert(view: View){
        var db = DBHelper(this)
        var insert_phone= EditText(this)
        val dialog= AlertDialog.Builder(view.context)
        dialog.setTitle("إضافة")
        dialog.setView(insert_phone)
        dialog.setPositiveButton("إضافة ", ){dialog,id->
            if (insert_phone.text.toString().isEmpty()){
                Toast.makeText(this, "الرجاء إدخال قيمة", Toast.LENGTH_LONG).show()
            }else{
                db.insert(insert_phone.text.toString())
                Toast.makeText(this, "تمت الإضافة بشكل صحيح", Toast.LENGTH_LONG).show()
            }

        }
        dialog.setNegativeButton("إلغاء", null)
        dialog.show()
    }

    fun show(view:View){
        var phoneList: List<String> =dbHelper.viewPhone()
        var myListAdapter= ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,phoneList)
        listView.adapter= myListAdapter
    }


}








