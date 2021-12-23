package com.example.gameroom.model

class Room( var namesOfTheRoom:String,  //назва кімнати
            var money:Double,           //к-сть грошей
            var idRoom:Int = -100, )    //номер кімнати
{
    override fun toString(): String {

        return "Room ID:${idRoom} --- ${namesOfTheRoom} money: ${money}  \n"
    }
}