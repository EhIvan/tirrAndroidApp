package com.example.tirrapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.tirrapp.constance.Constance
import com.example.tirrapp.databinding.ContestLayoutBinding
import com.example.tirrapp.db.DbFunction

class LayoutContest : AppCompatActivity() {
    lateinit var ContestLayoutBindingClass : ContestLayoutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ContestLayoutBindingClass = ContestLayoutBinding.inflate(layoutInflater)
        setContentView(ContestLayoutBindingClass.root)
        val rank_name = intent.getStringExtra(Constance.rank_state)
        val DbFunction = DbFunction(this)

        when (rank_name){
            Constance.rank_strelok ->{
                ContestLayoutBindingClass.buttonContestNew.visibility = View.GONE
            }
            Constance.rank_new ->{
                ContestLayoutBindingClass.buttonContestNew.visibility = View.GONE
                ContestLayoutBindingClass.buttonContestOld.visibility = View.GONE
                ContestLayoutBindingClass.buttonContestExist.visibility = View.GONE
            }
        }

        ContestLayoutBindingClass.buttonContestOld.setOnClickListener {
            DbFunction.openDb()
        }


        ContestLayoutBindingClass.buttonContestExist.setOnClickListener {
            DbFunction.closeDb()
        }


        ContestLayoutBindingClass.buttonContestNew.setOnClickListener {
            val intent = Intent(this,LayoutNewContest::class.java)
            intent.putExtra(Constance.event_type, Constance.competition_event_type)
            startActivity(intent)

        }

        ContestLayoutBindingClass.btnCancel.setOnClickListener {
            finish()
        }

    }

}