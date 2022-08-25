package com.example.tirrapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tirrapp.constance.Constance
import com.example.tirrapp.db.DbFunction

// Слой для загрузки еактуальных эвентов.

class EventsActivity : AppCompatActivity() {

    val db = DbFunction(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)
        db.openDb()
        init()
    }

    fun init(){
        val recyclerView: RecyclerView = findViewById(R.id.rcView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyEventAdapter(db.getAheadEvents(Constance.training_event_type))

    }

}