package com.example.gameroom.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class MyDbHelper(context: Context) : SQLiteOpenHelper(context,MyDbNameClass.DATABASE_NAME, null, MyDbNameClass.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(MyDbNameClass.CREATE_TABLE_ALL_ROOMS)
        db?.execSQL(MyDbNameClass.CREATE_TABLE_ALL_TOYS)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(MyDbNameClass.DELETE_TABLE_ALL_ROOMS)  //видаляємо таблиці
        db?.execSQL(MyDbNameClass.DELETE_TABLE_ALL_TOYS)
        onCreate(db)                             //створюємо нову bd
    }

}