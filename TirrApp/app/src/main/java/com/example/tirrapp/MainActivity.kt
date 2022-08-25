package com.example.tirrapp


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.core.text.set
import androidx.fragment.app.Fragment

import com.example.tirrapp.databinding.ActivityMainBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity() {
    //Список переменных:
    //   val bnt_main_contest: Button = findViewById(R.id.button_main_contest)
    //   val bnt_main_workout: Button = findViewById(R.id.button_main_contest)
    //   val bnt_main_exercise: Button = findViewById(R.id.button_main_exercise)
    //   val bnt_main_personal_data: Button = findViewById(R.id.button_main_personal_data)

    lateinit var ActivityMainbindingClass: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainbindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ActivityMainbindingClass.root)

        var date: Long = 0
        var time: Long
        val sdf = SimpleDateFormat("MM/dd/yyyy HH:mm")

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        Log.d("MyLog", "ещвфн ${MaterialDatePicker.todayInUtcMilliseconds()}")

        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(9)
            .setMinute(0)
            .build()

        fun checkDate(date: Long) {
            if (MaterialDatePicker.todayInUtcMilliseconds() >= date) {
                Toast.makeText(
                    applicationContext,
                    "Выбираемая дата не может быть в прошлом!",
                    Toast.LENGTH_LONG
                ).show()
                datePicker.onDestroy()
                //supportFragmentManager.findFragmentByTag("tag")?.onDestroyView()
                datePicker.show(supportFragmentManager, "tag")
            } else {
                ActivityMainbindingClass.textView.text = datePicker.selection.toString()
                //date = datePicker.selection!!.toLong()
                //ActivityMainbindingClass.tv.text = Editable.Factory.getInstance().newEditable(text)

            }
        }



        datePicker.addOnPositiveButtonClickListener {
            date = datePicker.selection!!.toLong()
            timePicker.show(supportFragmentManager, "tag")
                    }



        timePicker.addOnPositiveButtonClickListener {
            time = date.plus((timePicker.hour * 3.6e+6).toLong())
                .plus((timePicker.minute * 60000).toLong())
            ActivityMainbindingClass.tv.text =
                Editable.Factory.getInstance().newEditable(sdf.format(time))
            checkDate(date)

        }

        ActivityMainbindingClass.datetime.setOnClickListener {
            //dateTime (supportFragmentManager)
            //datePicker.show(supportFragmentManager, "tag")
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }


        ActivityMainbindingClass.buttonMainContest.setOnClickListener {
            val intent = Intent(this, LayoutContest::class.java)
            startActivity(intent)
        }
        ActivityMainbindingClass.buttonMainWorkout.setOnClickListener {
            val intent = Intent(this, TrainingActivity::class.java)
            startActivity(intent)
        }

    }
}