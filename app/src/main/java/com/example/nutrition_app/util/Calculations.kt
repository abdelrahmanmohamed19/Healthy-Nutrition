package com.example.nutrition_app.util

import com.example.nutrition_app.data.model.HealthAdvice
import com.example.nutrition_app.data.model.Meal

class Calculations {

    fun calculateCustomGramsCalories(caloriesOf100g: Double, mealGrams: Double) =
        if (mealGrams <= 0 || caloriesOf100g <= 0) 0.0 else (mealGrams / 100.0) * caloriesOf100g

    fun calculatePersonDataCalories(
        gender: Char,
        weight: Double,
        height: Double,
        age: Int
    ): Double {
        return when (gender) {
            Constants.KeyValues.MALE -> if (weight <= 0 || height <= 0 || age <= 0) 0.0
            else String.format("%.2f", 66.0 + (13.7 * weight) + (5.0 * height) - (6.8 * age))
                .toDouble()
            Constants.KeyValues.FEMALE -> if (weight <= 0 || height <= 0 || age <= 0) 0.0
            else String.format("%.2f", 665.0 + (9.6 * weight) + (1.8 * height) - (4.7 * age))
                .toDouble()
            else -> 0.0
        }

    }

    fun getMealListByMealSubName(mealSubName: String, mealList: List<Meal>): List<Meal> {
        val mealListStarsWithSubName =
            mealList.filter { it.name.lowercase().startsWith(mealSubName.lowercase()) }
        val mealListContainsSubName = mealList.filter {
            it.name.lowercase().contains(mealSubName.lowercase()) && !it.name.lowercase()
                .startsWith(mealSubName.lowercase())
        }
        return mealListStarsWithSubName + mealListContainsSubName
    }

    fun getListByMealName(mealName: String, mealList: List<Meal>): Meal? {
        mealList.forEach {
            if (it.name == mealName) return it
        }
        return null
    }

    fun diabeticsBestMeals(mealsList: MutableList<Meal>, top: Int): List<Meal>? {
        if (mealsList.isEmpty() || top <= 0 || top > mealsList.size) return null
        mealsList.sortByDescending {
            it.potassium + 0.7 * it.carbohydrate + 0.5 * it.fiber - it.sugars
        }
        return mealsList.take(top)
    }

    fun bodyBuildingBestMeals(mealsList: MutableList<Meal>, top: Int): List<Meal>? {
        if (mealsList.isEmpty() || top <= 0 || top > mealsList.size) return null
        mealsList.sortByDescending {
            0.4 * it.protein + 0.15 * it.totalFat + 0.45 * it.carbohydrate
        }
        return mealsList.take(top)
    }


    fun weightLossBestMeals(mealsList: MutableList<Meal>, top: Int): List<Meal>? {

        if (mealsList.isEmpty() || top <= 0 || top > mealsList.size) return null
        mealsList.sortByDescending {
            0.5 * it.protein + 0.2 * it.totalFat + 0.3 * it.carbohydrate
        }
        return mealsList.take(top)
    }

    fun bloodPressureBestMeals(mealsList: MutableList<Meal>, top: Int): List<Meal>? {
        if (mealsList.isEmpty() || top <= 0 || top > mealsList.size) return null
        mealsList.sortByDescending {
            it.calcium + 0.7 * it.fiber - it.sodium - it.totalFat
        }
        return mealsList.take(top)
    }

    fun getRandomAdvice(healthAdviceList: MutableList<HealthAdvice>): HealthAdvice {
        val randomIndex = (0 until healthAdviceList.size).random()
        return healthAdviceList[randomIndex]
    }

    fun sortCalories(mealsList: MutableList<Meal>) = mealsList.sortedByDescending { it.calories }
    fun sortTotalFat(mealsList: MutableList<Meal>) = mealsList.sortedByDescending { it.totalFat }
    fun sortFiber(mealsList: MutableList<Meal>) = mealsList.sortedByDescending { it.fiber }
    fun sortSugars(mealsList: MutableList<Meal>) = mealsList.sortedByDescending { it.sugars }
    fun sortProtein(mealsList: MutableList<Meal>) = mealsList.sortedByDescending { it.protein }
    fun sortVitaminD(mealsList: MutableList<Meal>) = mealsList.sortedByDescending { it.vitaminD }
    fun sortCarbohydrate(mealsList: MutableList<Meal>) = mealsList.sortedByDescending { it.carbohydrate }

}