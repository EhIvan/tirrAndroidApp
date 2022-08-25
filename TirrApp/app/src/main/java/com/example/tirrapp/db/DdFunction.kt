package com.example.tirrapp.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import com.example.tirrapp.DataClass.EventShort
import com.google.android.material.datepicker.MaterialDatePicker.todayInUtcMilliseconds

class DbFunction(context: Context) {
    val myDbHelper = MyDbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb(){
        db = myDbHelper.writableDatabase
    }

    fun insertToDbEvent(name_event: String, discipline: String, scope: String, status: String,
                   date: String, place: String, instructor: String, event_type: String){
        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_NAME, name_event)
            put(MyDbNameClass.COLUMN_NAME_DISCIPLINE, discipline)
            put(MyDbNameClass.COLUMN_NAME_SCOPE, scope)
            put(MyDbNameClass.COLUMN_NAME_STATUS, status)
            put(MyDbNameClass.COLUMN_NAME_DATE, date)
            put(MyDbNameClass.COLUMN_NAME_PLACE, place)
            put(MyDbNameClass.COLUMN_NAME_INSTRUCTOR, instructor)
            put(MyDbNameClass.COLUMN_NAME_EVENT_TYPE, event_type)

        }
        db?.insert(MyDbNameClass.TABLE_NAME, null, values)
    }
    @SuppressLint("Range")
    fun insertToDbUser (surname: String, name : String, category: String) : Int? {
        val values = ContentValues().apply{
            put(MyDbNameClass.TABLE_USERS_SURNAME, surname)
            put(MyDbNameClass.TABLE_USERS_NAME, name)
            put(MyDbNameClass.TABLE_USERS_CATEGORY, category)
        }
        db?.insert(MyDbNameClass.TABLE_USERS, null, values)
        val selection = "${MyDbNameClass.TABLE_USERS_SURNAME} = ? and ${MyDbNameClass.TABLE_USERS_NAME} = ? and ${MyDbNameClass.TABLE_USERS_CATEGORY} = ?"
        val selectionArgs = arrayOf(surname, name, category)
        val cursor = db?.query(
            MyDbNameClass.TABLE_USERS,
            arrayOf(BaseColumns._ID),
            selection,
            selectionArgs,
            null,
            null,
            null)
        Log.d("MyLog", "КУрсор: $cursor")
        Log.d("MyLog", "КУрсор: ${cursor?.count}")
        var id : Int
        if(cursor?.count == 1){
            cursor.moveToFirst()
            id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            Log.d("MyLog", "в функции БД: $id")
            cursor.close()
        }else id = -100
        return id
    }

    fun insertToDbUsersCategory(id: Int, category: String, powerFactor: String){
        Log.d("MyLog", "в insertToDbUsersCategory: $id")
        val values = ContentValues().apply {
            put(MyDbNameClass.TABLE_USER_CATEGORY_ID, id)
            put(MyDbNameClass.TABLE_USER_CATEGORY_CATEGORY, category)
            put(MyDbNameClass.TABLE_USER_CATEGORY_POWER_FACTOR, powerFactor)
        }
        db?.insert(MyDbNameClass.TABLE_USER_CATEGORY, null, values)
    }

    @SuppressLint("Range")
    fun readDbEvents(): ArrayList<String>{
        val today = todayInUtcMilliseconds()
        val dataList = ArrayList<String>()
        val cursor = db?.query(MyDbNameClass.TABLE_NAME, null, null,
            null, null, null, null, null)
        with (cursor){
            while (this?.moveToNext()!!){
                val dataText = cursor?.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_NAME))
                dataList.add(dataText.toString())
            }
        }
        cursor?.close()
        return dataList
    }

    @SuppressLint("Range")
    fun getAheadEvents(event_type: String) :ArrayList<EventShort>{
        val today = todayInUtcMilliseconds()
        val dataList = ArrayList<EventShort>()
        val request = "select name_event, discipline, date, event_type, _id from events where date>$today and event_type='$event_type'"

        val cursor = db?.rawQuery(request, null)
        while (cursor?.moveToNext()!!){
            val datadata = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_DATE))
            val dataname = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_NAME))
            val datadiscipline = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_DISCIPLINE))
            val dataid = cursor.getString(cursor.getColumnIndex("_id"))

            val eventShort = EventShort(
                dataname,
                datadata,
                datadiscipline,
                dataid
            )
            dataList.add(eventShort)
        }

        cursor.close()
        return dataList
    }


    fun deleteEvent(id: String){
        // Define 'where' part of query.
        val selection = "_id = ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(id)
        // Issue SQL statement.
        val deletedRows = db?.delete(MyDbNameClass.TABLE_NAME, selection, selectionArgs)
    }

    fun closeDb (){
        myDbHelper.close()
    }
}

