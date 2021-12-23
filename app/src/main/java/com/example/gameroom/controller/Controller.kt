package com.example.gameroom.controller


import com.example.gameroom.model.Room
import com.example.gameroom.model.Toy

object Controller {
        private var toysInTheRoom = ArrayList<Toy>()   //іграшки в кімнаті
        private var toysInTheToyStore = ArrayList<Toy>() //іграшки в магазині
        private var room: Room = Room("",0.0)   //кімната




        fun addMoney(money_param:Double )  {
                room.money = room.money+money_param
        }
        fun subMoney(money_param:Double )  {
                room.money = room.money-money_param
        }
        fun setToysInTheRoom(toys:ArrayList<Toy> )  {
                toysInTheRoom.addAll(toys)
        }
        fun setToysInTheToyStore(toys:ArrayList<Toy> )  {
                toysInTheToyStore.addAll(toys)
        }
        fun setIdRoom(id_param:Int )  {
                room.idRoom = id_param
        }
        fun setMoney(money_param:Double )  {
                room.money = money_param
        }
        fun setName(name_param:String){
                room.namesOfTheRoom = name_param
        }
        fun getToysInTheRoom() : ArrayList<Toy> {
                return toysInTheRoom
        }
        fun getToysInTheToyStore() : ArrayList<Toy> {
                return toysInTheToyStore
        }
        fun getMoney():Double{
                return room.money
        }
        fun getIdRoom():Int{
                return room.idRoom
        }
        fun getNameOfTheRoom():String{
                return room.namesOfTheRoom
        }

        fun clearData(){     //функція для зміни кімнати, очищає всі дані
                toysInTheRoom.clear()
                toysInTheToyStore.clear()
                room.idRoom =-100
                room.namesOfTheRoom =""
                room.money = 0.0
        }
        override fun toString(): String {
                return "Controller ID:${room.idRoom} --- ${room.namesOfTheRoom}\n"

        }
}