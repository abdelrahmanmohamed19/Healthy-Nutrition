package com.example.nutrition_app.data.dataManager

import android.os.Parcel
import android.os.Parcelable
import com.example.nutrition_app.data.model.Meal

class MealDataManager() : Parcelable {

    private val meals: MutableList<Meal> = mutableListOf()

    fun addMeal(meal: Meal) {
        meals.add(meal)
    }

    fun getMeals(): MutableList<Meal> = meals

    constructor(parcel: Parcel) : this()

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MealDataManager> {
        override fun createFromParcel(parcel: Parcel): MealDataManager {
            return MealDataManager(parcel)
        }

        override fun newArray(size: Int): Array<MealDataManager?> {
            return arrayOfNulls(size)
        }
    }

}