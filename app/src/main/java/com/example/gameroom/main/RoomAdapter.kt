package com.example.gameroom.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gameroom.R
import com.example.gameroom.model.Room
import com.example.gameroom.controller.Controller
import com.example.gameroom.databinding.RoomItemBinding
import com.example.gameroom.db.MyDbManager

class RoomAdapter(var dbManager: MyDbManager, var goToRoom: onClick) :
    RecyclerView.Adapter<RoomAdapter.RoomHolder>() {

    private var roomList = ArrayList<Room>()

    interface onClick {
        fun startActivity()
    }

    class RoomHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = RoomItemBinding.bind(item)
        val buttonOpen = binding.imButtonOpen
        val buttonRemove = binding.imButtonRemove

        fun bind(room: Room) = with(binding) {
            textRoomName.text = room.namesOfTheRoom
            textMoneyInTheRoom.text = room.money.toString()
        }
    }

    //надуває шаблон - room_item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.room_item, parent, false)
        return RoomHolder(view)
    }

    //заповлюємо шаблон
    override fun onBindViewHolder(holder: RoomHolder, position: Int) {
        holder.bind(roomList[position])

        //відкривання кімнати
        holder.buttonOpen.setOnClickListener {
            //заповнюємо контроллер

            Controller.setToysInTheToyStore(dbManager.getToysInTheStoreFromBb(roomList[position].idRoom)!!) //додаємо іграшки в магазин
            Controller.setToysInTheRoom(dbManager.getToysInTheRoomFromBb(roomList[position].idRoom)!!) //додаємо іграшки в кімнату
            Controller.setName(roomList[position].namesOfTheRoom)
            Controller.setIdRoom(roomList[position].idRoom)
            Controller.setMoney(roomList[position].money)
            goToRoom.startActivity()   //метод інтерфейса
        }

        //видалення кімнати
        holder.buttonRemove.setOnClickListener {

            dbManager.deleteRoomFromBb(roomList[position]) //видаляємо всю кімнату з бд
            roomList.removeAt(position)   //видаляємо з списка

            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return roomList.size
    }

    fun update() {
        roomList = dbManager.getRoomsFromBb()!!
        notifyDataSetChanged()
    }
}