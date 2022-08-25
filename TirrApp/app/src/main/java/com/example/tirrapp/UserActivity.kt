package com.example.tirrapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.tirrapp.databinding.ActivityUserBinding
import com.example.tirrapp.db.DbFunction

class UserActivity : AppCompatActivity() {
    val DbFunction = DbFunction(this)
    lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonCancel.setOnClickListener {
            finish()
        }

        // Получения списка категорий.
        binding.spinnerCategory.adapter = ArrayAdapter.createFromResource(this,R.array.category,R.layout.dropdown_item)

        // Показывает/скрывает Switch при нажатии на CheckBox
        binding.checkBoxPCC.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                binding.switchPCC.visibility = View.VISIBLE
                }
            else{binding.switchPCC.visibility = View.GONE
                }}
        // Показывает/скрывает Switch при нажатии на CheckBox
        binding.checkBoxPistol.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                binding.switchPistol.visibility = View.VISIBLE
                }
            else{binding.switchPistol.visibility = View.GONE
                }}
        // Показывает/скрывает Switch при нажатии на CheckBox
        binding.checkBoxArbine.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                binding.switchArbine.visibility = View.VISIBLE
                }
            else{binding.switchArbine.visibility = View.GONE
                }}


        // Смена Minor/Major при нажатии на Switch
        binding.switchPCC.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                binding.switchPCC.text = getString(R.string.major)
            }else{
                binding.switchPCC.text = getString(R.string.minor)
            }
        }

        // Смена Minor/Major при нажатии на Switch
        binding.switchPistol.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                binding.switchPistol.text = getString(R.string.major)
            }else{
                binding.switchPistol.text = getString(R.string.minor)
            }
        }

        // Смена Minor/Major при нажатии на Switch
        binding.switchArbine.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                binding.switchArbine.text = getString(R.string.major)
            }else{
                binding.switchArbine.text = getString(R.string.minor)
            }
        }

        binding.tiName.addTextChangedListener(){

        }

        binding.buttonSave.setOnClickListener {
            val surname = binding.tiSurname.text.toString()
            val name = binding.tiName.text.toString()
            val category = binding.spinnerCategory.selectedItem.toString()
            DbFunction.openDb()
            val id = DbFunction.insertToDbUser(surname, name, category)
            if (id == -100){
                Toast.makeText(
                    applicationContext, "Пользователь с таким именем и фамилией уже создан ранее! Измените параметры и удалите ранее созданный дубликат.",
                    Toast.LENGTH_LONG).show()
                binding.tiSurname.text!!.clear()
                binding.tiName.text!!.clear()
            }else{
            Log.d("MyLog", "Тип события: $id")

            if(binding.checkBoxActionAir.isChecked) {DbFunction.insertToDbUsersCategory(
                id!!,
                getString(R.string.action_air),
                "null")
            }

            if(binding.checkBoxGun.isChecked) {DbFunction.insertToDbUsersCategory(
                id!!,
                getString(R.string.gun),
                "null")
            }

            if (binding.checkBoxArbine.isChecked){
                when(binding.switchArbine.isChecked){
                    true ->  DbFunction.insertToDbUsersCategory(
                        id!!,
                        getString(R.string.carbine),
                        getString(R.string.major))
                    false -> DbFunction.insertToDbUsersCategory(
                        id!!,
                        getString(R.string.carbine),
                        getString(R.string.minor))
                }
            }

            if (binding.checkBoxPCC.isChecked){
                when(binding.switchPCC.isChecked){
                    true ->  DbFunction.insertToDbUsersCategory(
                        id!!,
                        getString(R.string.PCC),
                        getString(R.string.major))
                    false -> DbFunction.insertToDbUsersCategory(
                        id!!,
                        getString(R.string.PCC),
                        getString(R.string.minor))
                }
            }

            if (binding.checkBoxPistol.isChecked){
                when(binding.switchPistol.isChecked){
                    true ->  DbFunction.insertToDbUsersCategory(
                        id!!,
                        getString(R.string.pistol),
                        getString(R.string.major))
                    false -> DbFunction.insertToDbUsersCategory(
                        id!!,
                        getString(R.string.pistol),
                        getString(R.string.minor))
                }
            }


        }
            finish()
        }




    }

    fun insertToDbCategory(id: Int, category: String, checkBoxStatus: Boolean, switchStatus: Boolean){
        if(checkBoxStatus){
            when(switchStatus){
                true ->  DbFunction.insertToDbUsersCategory(
                    id,
                    category,
                    getString(R.string.major))
                false -> DbFunction.insertToDbUsersCategory(
                    id,
                    category,
                    getString(R.string.minor))
            }
        }
    }


}

