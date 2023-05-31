package com.example.nutrition_app.util

object Constants {
    object ColumnIndex {
        const val NAME = 1
        const val CALORIES = 3
        const val TOTAL_FAT = 4
        const val SODIUM = 7
        const val VITAMIN_C = 24
        const val VITAMIN_D = 25
        const val CALCIUM = 29
        const val MAGNESIUM = 32
        const val POTASSIUM = 35
        const val PROTEIN = 38
        const val CARBOHYDRATE = 58
        const val FIBER = 59
        const val SUGARS = 60
        const val CAFFEINE = 74
        const val TITLE = 1
        const val DETAILS = 2
    }

    object FilePath {
        const val NUTRITION_CSV = "nutrition.csv"
        const val HEALTH_ADVICES_CSV = "health_advices.csv"
    }

    object KeyValues {
        const val MEAL = "meal"
        const val Meal_DATA_MANAGER = "mealDataManger"
        const val HEALTH_ADVICE_DATA_MANAGER = "healthAdviceDataManger"
        const val MALE = 'M'
        const val FEMALE = 'F'
        const val BEST_MEAL_TYPE = "bestMealType"
        const val WEIGHT_LOSS = "WeightLoss"
        const val DIABETICS = "Diabetics"
        const val PRESSURE = "Pressure"
        const val GYM = "Gym"
    }

    object Data{
        object LocalStorage{
            const val NAME = "LocalStorage"
            const val MY_DATA = "myData"
        }

    }
}