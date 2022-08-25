package com.example.tirrapp

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.tirrapp.constance.Constance
import com.example.tirrapp.databinding.ContestNewLayoutBinding
import com.example.tirrapp.db.DbFunction
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat

class LayoutNewContest : AppCompatActivity() {
    lateinit var contestNewLayoutBindingClass: ContestNewLayoutBinding
    val DbFunction = DbFunction(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contestNewLayoutBindingClass = ContestNewLayoutBinding.inflate(layoutInflater)
        setContentView(contestNewLayoutBindingClass.root)


        var name_event: String
        var discipline: String
        var scope: String
        var status: String
        var place: String
        var instructor: String
        var dateTime: String = "empty"
        var date: Long = 0
        var time: Long
        val event_type = intent.getStringExtra(Constance.event_type).toString()
        Log.d("MyLog", "Тип события: ${event_type}")

        if (event_type == Constance.training_event_type) {
            contestNewLayoutBindingClass.tilayoutStatus.visibility = View.GONE
        }


        val sdf = SimpleDateFormat("MM/dd/yyyy HH:mm")

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(9)
            .setMinute(0)
            .build()


        contestNewLayoutBindingClass.tiData.setOnClickListener {
            datePicker.show(supportFragmentManager, "tag")
        }

        datePicker.addOnPositiveButtonClickListener {
            date = datePicker.selection!!.toLong()
            timePicker.show(supportFragmentManager, "tag")
        }

        timePicker.addOnPositiveButtonClickListener {
            if (MaterialDatePicker.todayInUtcMilliseconds() >= date) {
                Toast.makeText(
                    applicationContext,
                    "Выбираемая дата не может быть в прошлом!",
                    Toast.LENGTH_LONG
                ).show()
                datePicker.onDestroy()
                datePicker.show(supportFragmentManager, "tag")
            } else {
                time = date.plus((timePicker.hour * 3.6e+6).toLong())
                    .plus((timePicker.minute * 60000).toLong())
                contestNewLayoutBindingClass.tiData.text =
                    Editable.Factory.getInstance().newEditable(sdf.format(time))
                dateTime = time.toString()
            }
        }

        //Выпадающий список с дисциплинами.
        val autoCompleteTextView: AutoCompleteTextView = contestNewLayoutBindingClass.tvDiscipline
        val disciplineArray = resources.getStringArray(R.array.discipline)
        autoCompleteTextView.setAdapter(ArrayAdapter(this, R.layout.dropdown_item, disciplineArray))

        //Выпадающий список со статусами.
        val autoCompleteTextViewstatus: AutoCompleteTextView = contestNewLayoutBindingClass.tvStatus
        val statusArray = resources.getStringArray(R.array.status)
        autoCompleteTextViewstatus.setAdapter(ArrayAdapter(this, R.layout.dropdown_item, statusArray))


        //Выпадающий список типов.
        val autoCompleteTextViewscope: AutoCompleteTextView = contestNewLayoutBindingClass.tvScope
        val scopeArray = resources.getStringArray(R.array.scope)
        autoCompleteTextViewscope.setAdapter(ArrayAdapter(this, R.layout.dropdown_item, scopeArray))

        //Выпадающий список мест.
        val autoCompleteTextViewplace: AutoCompleteTextView = contestNewLayoutBindingClass.tvPlace
        val placeArray = resources.getStringArray(R.array.place)
        autoCompleteTextViewplace.setAdapter(ArrayAdapter(this, R.layout.dropdown_item, placeArray))

        //Проверка на слишком короткое название.
        contestNewLayoutBindingClass.tiNameContest.doOnTextChanged { text, _start, before, count ->
            if (text!!.length < 5) {
                contestNewLayoutBindingClass.tiNameContest.error = "Слишком короткое название"
            } else {
                contestNewLayoutBindingClass.tiNameContest.error = null
            }
        }

        //Обработка кнопки сохранить.
        contestNewLayoutBindingClass.btnSave.setOnClickListener {

            name_event = contestNewLayoutBindingClass.tiNameContest.text.toString()
            discipline = contestNewLayoutBindingClass.tvDiscipline.text.toString()
            scope = contestNewLayoutBindingClass.tvScope.text.toString()
            status = contestNewLayoutBindingClass.tvStatus.text.toString()
            place = contestNewLayoutBindingClass.tvPlace.text.toString()
            instructor = contestNewLayoutBindingClass.tvTrener.text.toString()

            if (dateTime == "empty" || place == "" || discipline ==  "" || scope ==  ""|| name_event ==  "") {
                Toast.makeText(
                    applicationContext, "Заполните все обязательные поля поля!",
                    Toast.LENGTH_LONG
                ).show()
            }else{

            with(DbFunction) {
                openDb()
                insertToDbEvent(
                    name_event,
                    discipline,
                    scope,
                    status,
                    dateTime,
                    place,
                    instructor,
                    event_type
                )
            }
                DbFunction.closeDb()
                Toast.makeText(
                    applicationContext, "Мероприятие создано!",
                    Toast.LENGTH_LONG)
                finish()
            }
        }

        val btn_cancel: Button = findViewById(R.id.btn_cancel)
        btn_cancel.setOnClickListener {
            finish()
        }

    }
}