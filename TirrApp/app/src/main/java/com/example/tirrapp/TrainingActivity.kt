package com.example.tirrapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.tirrapp.constance.Constance
import com.example.tirrapp.databinding.ActivityTrainingBinding
import com.example.tirrapp.db.DbFunction
import com.google.android.material.datepicker.MaterialDatePicker

class TrainingActivity : AppCompatActivity() {
    val DbFunction = DbFunction(this)
    lateinit var binding: ActivityTrainingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrainingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTrainingNew.setOnClickListener {
            val intent = Intent(this,LayoutNewContest::class.java)
            intent.putExtra(Constance.event_type, Constance.training_event_type)
            startActivity(intent)
        }

        binding.btnTrainingList.setOnClickListener {
            startActivity(Intent(this,EventsActivity::class.java ))
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }

    }
}