package com.example.gameroom.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.example.gameroom.model.Room
import com.example.gameroom.model.Toy
import java.util.ArrayList

class MyDbManager(context: Context) {

    val myDbHelper = MyDbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb(){     //відкриваємо базу даних для запису
        db = myDbHelper.writableDatabase
    }

    fun insertNewRoomToDb(name: String,money:Double):Int{
        //створюємо нову кімнату
        //зберігаємо в мапу дані
        val contentValues = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_NAME_OF_THE_ROOM, name)
            put(MyDbNameClass.COLUMN_NAME_MONEY, money)
        }
        //вставляємо в таблицю новий рядок
        db?.insert(MyDbNameClass.TABLE_NAME_ALL_ROOMS, null, contentValues)

        //повертаємо айді щойно створеної кімнати
       val cursor = db!!.query(MyDbNameClass.TABLE_NAME_ALL_ROOMS, arrayOf(BaseColumns._ID)
           , MyDbNameClass.COLUMN_NAME_NAME_OF_THE_ROOM +"=?", arrayOf(name), null, null, null)
        var result:Int
        cursor.moveToNext()
        result=cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
        cursor.close()

        return result
    }

    fun upgradeMoney(idRoom:Int, money:Double){
        val contentValues = ContentValues()
        contentValues.put(MyDbNameClass.COLUMN_NAME_MONEY,money)
        db?.update(MyDbNameClass.TABLE_NAME_ALL_ROOMS,contentValues,BaseColumns._ID+"=?", arrayOf(idRoom.toString()))
    }
    fun insertToysToDb(idRoom:Int,inRoom:Int,toy: Toy) {
        //зберігаємо в мапу дані

        val contentValues = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_ID_ROOM, idRoom)
            put(MyDbNameClass.COLUMN_NAME_IN_ROOM, inRoom)
            put(MyDbNameClass.COLUMN_NAME_NAME_OF_THE_TOY, toy.name)
            put(MyDbNameClass.COLUMN_NAME_WEIGHT,toy.weight)
            put(MyDbNameClass.COLUMN_NAME_COLOR, toy.color)
            put(MyDbNameClass.COLUMN_NAME_SIZE, toy.size)
            put(MyDbNameClass.COLUMN_NAME_PRICE, toy.price)
        }
        //вставляємо в таблицю новий рядок
        db?.insert(MyDbNameClass.TABLE_NAME_ALL_TOYS, null, contentValues)
    }

    fun deleteRoomFromBb(room: Room){
        //видаляємо спочатку всі іграшки зкімнати
        deleteToysFromRoom(room.idRoom)
        //видаляємо саму кімнату
        db?.delete(MyDbNameClass.TABLE_NAME_ALL_ROOMS,BaseColumns._ID +"=?", arrayOf(room.idRoom.toString()) )

    }
    fun deleteToysFromRoom(roomId:Int){
        db?.delete(MyDbNameClass.TABLE_NAME_ALL_TOYS,MyDbNameClass.COLUMN_NAME_ID_ROOM +"=?", arrayOf(roomId.toString()) )
        //db?.delete(MyDbNameClass.TABLE_NAME_ALL_TOYS,MyDbNameClass.COLUMN_NAME_ID_ROOM +"="+ roomId.toString(),null)
    }

    fun getRoomsFromBb(): ArrayList<Room>? {
        val rooms: ArrayList<Room> = ArrayList<Room>()
        var room: Room
        val cursor = db!!.query(MyDbNameClass.TABLE_NAME_ALL_ROOMS, null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            room = Room(
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_NAME_OF_THE_TOY)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_MONEY)),
                cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID)),
            )
            rooms.add(room)
        }
        cursor.close()
        return rooms
    }
    fun getToysInTheRoomFromBb(roomId:Int): ArrayList<Toy>? {
        val toys: ArrayList<Toy> = ArrayList<Toy>()
        var toy: Toy
        val cursor = db!!.query(MyDbNameClass.TABLE_NAME_ALL_TOYS, null, MyDbNameClass.COLUMN_NAME_IN_ROOM + "=?" + " AND "+MyDbNameClass.COLUMN_NAME_ID_ROOM+ "=?",
            arrayOf(MyDbNameClass.IN_ROOM,roomId.toString()), null, null, null)
        while (cursor.moveToNext()) {
            toy = Toy(
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_NAME_OF_THE_TOY)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_WEIGHT)),
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_COLOR)),
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_SIZE)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_PRICE))
            )

            toys.add(toy)
        }
        cursor.close()
        return toys
    }
    fun getToysInTheStoreFromBb(roomId:Int): ArrayList<Toy>? {
        val toys: ArrayList<Toy> = ArrayList<Toy>()
        var toy: Toy
        val cursor = db!!.query(MyDbNameClass.TABLE_NAME_ALL_TOYS, null, MyDbNameClass.COLUMN_NAME_IN_ROOM + "=?" +" AND "+MyDbNameClass.COLUMN_NAME_ID_ROOM+ "=?",
            arrayOf(MyDbNameClass.IN_STORE,roomId.toString()), null, null, null)
        while (cursor.moveToNext()) {
            toy = Toy(
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_NAME_OF_THE_TOY)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_WEIGHT)),
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_COLOR)),
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_SIZE)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_PRICE))
            )
            toys.add(toy)
        }
        cursor.close()
        return toys
    }

    fun clodeDb() {
        myDbHelper.close()
    }

      fun getIdRoom(name:String):Int{

          val cursor = db!!.query(MyDbNameClass.TABLE_NAME_ALL_ROOMS, arrayOf(BaseColumns._ID), MyDbNameClass.COLUMN_NAME_NAME_OF_THE_ROOM +"=?", arrayOf(name),
              null, null, null)
          var result:Int
          result=cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
          cursor.close()
          return result
      }
}