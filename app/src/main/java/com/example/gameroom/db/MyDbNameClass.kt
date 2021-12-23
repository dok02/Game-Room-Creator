package com.example.gameroom.db

import android.provider.BaseColumns

object MyDbNameClass{

    const val TABLE_NAME_ALL_ROOMS = "Rooms"
    const val COLUMN_NAME_NAME_OF_THE_ROOM= "Name"
    const val COLUMN_NAME_MONEY= "Balance"

    const val TABLE_NAME_ALL_TOYS = "AllToys"
    const val COLUMN_NAME_ID_ROOM = "id_room"
    const val COLUMN_NAME_IN_ROOM = "InRoom"
    const val COLUMN_NAME_NAME_OF_THE_TOY = "Name"
    const val COLUMN_NAME_WEIGHT = "Weight"
    const val COLUMN_NAME_COLOR = "Color"
    const val COLUMN_NAME_SIZE = "Size"
    const val COLUMN_NAME_PRICE = "Price"

    const val IN_ROOM ="1"
    const val IN_STORE ="0"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "Game_room"

    const val CREATE_TABLE_ALL_ROOMS =
        "CREATE TABLE IF NOT EXISTS ${TABLE_NAME_ALL_ROOMS} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${COLUMN_NAME_NAME_OF_THE_TOY} TEXT," +
                "${COLUMN_NAME_MONEY} DOUBLE)"


    const val CREATE_TABLE_ALL_TOYS =
        "CREATE TABLE IF NOT EXISTS ${TABLE_NAME_ALL_TOYS} (" +
                "${COLUMN_NAME_ID_ROOM} INTEGER," +
                "${COLUMN_NAME_IN_ROOM} INTEGER," +
                "${COLUMN_NAME_NAME_OF_THE_TOY} TEXT," +
                "${COLUMN_NAME_WEIGHT} DOUBLE," +
                "${COLUMN_NAME_COLOR} TEXT," +
                "${COLUMN_NAME_SIZE} TEXT," +
                "${COLUMN_NAME_PRICE} DOUBLE)"

    const val DELETE_TABLE_ALL_ROOMS = "DROP TABLE IF EXISTS ${TABLE_NAME_ALL_ROOMS}"
    const val DELETE_TABLE_ALL_TOYS = "DROP TABLE IF EXISTS ${TABLE_NAME_ALL_TOYS}"


}