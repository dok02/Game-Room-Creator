package com.example.gameroom.sort


import com.example.gameroom.model.Toy
import java.util.Comparator;

 class SortByCost : Comparator<Toy> {

 override fun compare(toy1: Toy, toy2: Toy): Int {

         return  ((toy1.price!!- toy2.price!!).toInt())
     }
 }

