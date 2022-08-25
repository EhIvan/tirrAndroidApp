package com.example.tirrapp.DataClass

import com.example.tirrapp.db.DbFunction
import java.text.SimpleDateFormat

data class EventShort(
    val event_name : String,
    val event_date : String,
    val event_discipline : String,
    val event_id : String

){
    fun convertDate() :String {
        val sdf = SimpleDateFormat("MM/dd/yyyy HH:mm")
        val temp = "Не работает"
        return if(event_date ==""){
            temp
        }else{
            sdf.format(event_date.toLong()).toString()
        }
    }


}
