package com.example.tirrapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tirrapp.DataClass.EventShort
import com.example.tirrapp.db.DbFunction
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyEventAdapter(listMain: ArrayList<EventShort>) :RecyclerView.Adapter<MyEventAdapter.MyHolder>() {

    var listArray = listMain



    class MyHolder(itemView: View, list: ArrayList<EventShort>) : RecyclerView.ViewHolder(itemView) {
        val item : View = itemView
        val _list = list
        val tv_name_event : TextView = itemView.findViewById(R.id.tvNameEvent)
        val tv_date_event : TextView = itemView.findViewById(R.id.tvDateEvent)
        val tv_discipline_event : TextView = itemView.findViewById(R.id.tvDisciplineEvent)
        val bnt_delete_event : FloatingActionButton =  itemView.findViewById(R.id.btnDeleteEvent)

        fun deleteEvent(id: String){
            val DbFunction = DbFunction(itemView.context)
            DbFunction.openDb()
            DbFunction.deleteEvent(id)
            MyEventAdapter(_list).updateAdapter(adapterPosition)
            DbFunction.closeDb()
        }


        fun setData(evenStShort : EventShort){
            tv_name_event.text= evenStShort.event_name
            tv_date_event.text= evenStShort.convertDate()
            tv_discipline_event.text= evenStShort.event_discipline


            bnt_delete_event.setOnClickListener {
                // Начало изменений
                deleteEvent(evenStShort.event_id)
                // Конец изменений
                tv_name_event.text= "Удалено!"

                Log.d("MyLog", "_id: ${evenStShort.event_id}")
                Log.d("MyLog", "_id: ${bnt_delete_event.id}")

                Toast.makeText(
                    itemView.context, "Мероприятие удалено!",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    //Запускаем шаблон
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyHolder(inflater.inflate(R.layout.rv_event, parent, false), listArray)
    }

    //Вставляет текст в шаблон.
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setData(listArray.get(position))
    }


    //Сколько элементов в списке
    override fun getItemCount(): Int {
        return listArray.size
    }

    fun updateAdapter(position: Int){
        listArray.removeAt(position)
        notifyItemRangeChanged(0, listArray.size)
        notifyItemRemoved(position)

    }

}