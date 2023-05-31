package com.example.nutrition_app.data

data class UserData(
    val email: String? = null,
    val name: String? = null,
    val age: String? = null,
    val weight: String? = null,
    val height: String? = null,
    val calories : String? = null,
    val protien : String? = null,
    val fats : String? = null,
    val carb : String? = null,
    val reCal : String? = calories,
    val reCarb : String? = carb,
    val rePro : String? = protien,
    val reFats : String? =fats
)
