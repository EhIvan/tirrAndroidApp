package com.example.tirrapp

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun dateTime (supportFragmentManager : FragmentManager) : String {
    val sdf = SimpleDateFormat("MM/dd/yyyy")
    val stf = SimpleDateFormat("HH:mm")
    var idate : Long
    var text : String = ""
    var text1 : String = ""

    val timePicker = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_24H)
        .setHour(9)
        .setMinute(0)
        .build()



    val datePicker =
        MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()



    datePicker.addOnPositiveButtonClickListener {
        // Respond to positive button click.
        idate = datePicker.selection.toString().toLong()
        val netDate = Date(idate)
        text = text + netDate.toString()
        Log.d("MyLog", "Текущая дата ${netDate.toString()}")
        timePicker.show(supportFragmentManager, "tag")
    }
    datePicker.addOnNegativeButtonClickListener {
        // Respond to negative button click.
        datePicker.onStop()
    }
    datePicker.addOnCancelListener {
        // Respond to cancel button click.
        datePicker.onStop()
    }



    timePicker.addOnPositiveButtonClickListener  {
        // call back code
        text = text + " в ${timePicker.hour}:${timePicker.minute}"

        Log.d("MyLog", "Текущ время ${timePicker.hour}:${timePicker.minute}")

    }
    timePicker.addOnCancelListener { timePicker.onStop() }
    timePicker.addOnNegativeButtonClickListener { timePicker.onStop() }
    timePicker.addOnDismissListener{ timePicker.onStop() }

    //val netDate = Date(idate)
    while (text =="" && text1 == ""){


    }

    Log.d("MyLog", "Проверка в функции ${datePicker.selection} ${timePicker.hour}")
    Log.d("MyLog", "Плог теквста $text")
    return text + text1

}
