package com.example.nutrition_app.util.extention
import com.example.nutrition_app.util.enum.StateNumber

fun Int.validityNumber(state: StateNumber): Boolean{
   return when(state){
       StateNumber.BIG -> this < 1000
       StateNumber.SMALL -> this !=0
    }
}



